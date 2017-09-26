package com.jara.kotlin_myshare.channel

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.jara.kotlin_myshare.R
import com.jara.kotlin_myshare.bean.Constant
import com.jara.kotlin_myshare.bean.ShareEntity
import com.jara.kotlin_myshare.http.Network
import com.jara.kotlin_myshare.interfaces.OnShareListener
import com.jara.kotlin_myshare.utils.ManifestUtil
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.*
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DefaultSubscriber
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Created by jara on 2017-9-19.
 */
class ShareByWeChatK(context: Context) : ShareBaseK(context) {

    var channel: Int? = null
    var entity: ShareEntity? = null
    var listener: OnShareListener? = null
    var iwxapi: IWXAPI? = null
    var receiver = WeChatShareReceiver()

    constructor(context: Context, channel: Int) : this(context) {
        this.context = context.applicationContext
        this.channel = channel
        iwxapi = WXAPIFactory.createWXAPI(context.applicationContext, ManifestUtil.getWeixinKey(this.context))
    }

    override fun share(entity: ShareEntity, listener: OnShareListener) {
        this.entity = entity
        this.listener = listener
        if (null == entity)
            return
        start()
    }

    fun start() {
        if (iwxapi!!.isWXAppInstalled) {
            val imgUrl = entity!!.imgUrl
            if (!TextUtils.isEmpty(imgUrl)) {
                if (imgUrl!!.startsWith("http")) {
                    getNetImage(imgUrl)
                } else {
                    if (entity!!.isShareBigImg) {
                        shareImg(getLocalBitmap(imgUrl), listener)
                    } else {
                        send(getLocalBitmap(imgUrl))
                    }
                }
            } else if (entity!!.drawableId != 0) {
                var drawable: BitmapDrawable? = null
                try {
                    drawable = ContextCompat.getDrawable(context, entity!!.drawableId) as BitmapDrawable?
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (null != drawable) {
                    send(drawable.bitmap)
                } else {
                    send()
                }
            } else {
                send()
            }
        } else {
            if (null != listener) {
                listener!!.onShare(channel!!, Constant.SHARE_STATUS_FAILED)
            }
        }
    }

    fun getLocalBitmap(path: String): Bitmap {
        val file = File(path)
        if (file.exists()) {
            try {
                return BitmapFactory.decodeFile(path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_share_default)
    }

    fun send(bitmap: Bitmap? = null) {
        val req = SendMessageToWX.Req()
        req.transaction = System.currentTimeMillis().toString()
        req.message = buildWXM(bitmap)
        if (channel == Constant.SHARE_CHANNEL_WEIXIN_FRIEND) {
            req.scene = SendMessageToWX.Req.WXSceneSession
        } else if (channel == Constant.SHARE_CHANNEL_WEIXIN_CIRCLE) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline
        }
        iwxapi!!.sendReq(req)
    }

    fun buildWXM(bitmap: Bitmap?): WXMediaMessage {
        val wxMediaMessage = WXMediaMessage()
        wxMediaMessage.title = entity!!.title
        wxMediaMessage.description = entity!!.content
        if (TextUtils.isEmpty(entity!!.url)) {
            wxMediaMessage.mediaObject = WXTextObject(entity!!.content)
        } else {
            if (null != bitmap) {
                wxMediaMessage.setThumbImage(getWxShareBitmap(bitmap))
            } else {
                val defaultBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_share_default)
                if (null != defaultBitmap) {
                    wxMediaMessage.setThumbImage(getWxShareBitmap(defaultBitmap))
                }
            }
            wxMediaMessage.mediaObject = WXWebpageObject(entity!!.url)
        }
        return wxMediaMessage
    }

    fun getWxShareBitmap(bitmap: Bitmap?): Bitmap {
        val scale: Float = Math.min(150 / bitmap!!.width.toFloat(), 150 / bitmap.height.toFloat())
        return Bitmap.createScaledBitmap(bitmap, (scale * bitmap.width).toInt(), (scale * bitmap.height).toInt(), false)
    }

    fun registerWeChatReceiver() {
        val filter = IntentFilter(Constant.ACTION_WEIXIN_CALLBACK)
        context.registerReceiver(receiver, filter)
    }

    fun unregisterWeChatReceiver() {
        if (null != context && null != receiver) {
            context.unregisterReceiver(receiver)
        }
    }

    fun getNetImage(imgUrl: String) {
        Network.getService()!!.getImage(imgUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DefaultSubscriber<ResponseBody>() {
                    override fun onComplete() {
                    }

                    override fun onError(t: Throwable?) {
                    }

                    override fun onNext(r: ResponseBody?) {
                        val stream = r!!.byteStream()
                        val bitmap = BitmapFactory.decodeStream(stream)
                        val baos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos)
                        baos.close()
                        if (entity!!.isShareBigImg) {
                            shareImg(bitmap, listener)
                        } else {
                            send(bitmap)
                        }
                    }
                })
    }

    inner class WeChatShareReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.hasExtra(Constant.EXTRA_WEIXIN_RESULT)) {
                val errCode = intent.getIntExtra(Constant.EXTRA_WEIXIN_RESULT, BaseResp.ErrCode.ERR_USER_CANCEL)
                val resultCode = if (errCode == BaseResp.ErrCode.ERR_OK) Activity.RESULT_OK else Activity.RESULT_CANCELED
                if (resultCode == Activity.RESULT_OK) {
                    if (null != listener) {
                        listener!!.onShare(channel!!, Constant.SHARE_STATUS_COMPLETE)
                    }
                } else {
                    if (null != listener) {
                        listener!!.onShare(channel!!, Constant.SHARE_STATUS_FAILED)
                    }
                }
            }
        }
    }

    companion object {
        @JvmField
        val THUMB_SIZE = 250
    }

    fun shareImg(bitmap: Bitmap?, listener: OnShareListener?) {
        if (null == bitmap) {
            listener?.onShare(channel!!, Constant.SHARE_STATUS_FAILED)
            return
        }
        if (iwxapi!!.isWXAppInstalled) {
            if (iwxapi!!.isWXAppSupportAPI) {
                val wxMediaMessage = WXMediaMessage()
                wxMediaMessage.mediaObject = WXImageObject(bitmap)
                var width = bitmap.width
                var height = bitmap.height
                while (width * height > THUMB_SIZE * THUMB_SIZE) {
                    width /= 2
                    height /= 2
                }
                val thumbBmp = Bitmap.createScaledBitmap(bitmap, width, height, true)
                try {
                    val baos = ByteArrayOutputStream()
                    thumbBmp.compress(Bitmap.CompressFormat.JPEG, 85, baos)
                    wxMediaMessage.thumbData = baos.toByteArray()
                    baos.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val req = SendMessageToWX.Req()
                req.transaction = System.currentTimeMillis().toString()
                req.message = wxMediaMessage
                when (channel) {
                    Constant.SHARE_CHANNEL_WEIXIN_FRIEND -> SendMessageToWX.Req.WXSceneSession
                    Constant.SHARE_CHANNEL_WEIXIN_CIRCLE -> SendMessageToWX.Req.WXSceneTimeline
                }
                iwxapi!!.sendReq(req)
            }
        } else {
            listener?.onShare(channel!!, Constant.SHARE_STATUS_FAILED)
        }
    }
}