package com.jara.kotlin_myshare.channel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.jara.kotlin_myshare.bean.Constant
import com.jara.kotlin_myshare.bean.ShareEntity
import com.jara.kotlin_myshare.interfaces.OnShareListener
import com.jara.kotlin_myshare.utils.ShareUtil
import com.jara.myshare.channel.ShareBaseK
import com.tencent.connect.share.QQShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError

/**
 * Created by jara on 2017-9-15.
 */
open class ShareByQQK: ShareBaseK{

    var tencent: Tencent

    constructor(context: Context) : super(context){
        tencent = Tencent.createInstance("", context.applicationContext)
    }
    override fun share(entity: ShareEntity, listener: OnShareListener) {
        if (null == entity) {
            return
        }
        if (context == null) {
            return
        }
        val callback= object : IUiListener {
            override fun onComplete(p0: Any?) {
                if (null != listener) {
                    listener.onShare(Constant.SHARE_CHANNEL_QQ, Constant.SHARE_STATUS_COMPLETE)
                }
            }

            override fun onCancel() {
                listener.onShare(Constant.SHARE_CHANNEL_QQ, Constant.SHARE_STATUS_CANCEL);
            }

            override fun onError(p0: UiError?) {
                listener?.onShare(Constant.SHARE_CHANNEL_QQ, Constant.SHARE_STATUS_FAILED)
            }
        }

        if (entity.isShareBigImg && !TextUtils.isEmpty(entity.imgUrl) && !entity.imgUrl!!.startsWith("http")) {
            var params : Bundle? = null
            params!!.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, entity.imgUrl)
            params!!.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)
            params!!.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE)
            tencent.shareToQQ(context as Activity?, params, callback)
        } else {
            var name = ""
            val intentSend = Intent(Intent.ACTION_SEND)
            intentSend.type = "text/plain"
            val listActivity = context.packageManager.queryIntentActivities(intentSend, 0)
            for (resolveInfo in listActivity) {
                if (TextUtils.equals(Constant.QQ_PACKAGE_NAME, resolveInfo.activityInfo.packageName)) {
                    name = resolveInfo.activityInfo.name
                    break
                }
            }
            val qqIntent = Intent(Intent.ACTION_SEND)
            qqIntent.type = "text/plain"
            qqIntent.putExtra(Intent.EXTRA_SUBJECT, entity.title)
            qqIntent.setClassName(Constant.QQ_PACKAGE_NAME, name)
            qqIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            qqIntent.putExtra(Intent.EXTRA_TEXT, entity.content)
        }
    }
}