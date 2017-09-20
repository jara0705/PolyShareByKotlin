package com.jara.kotlin_myshare

import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.jara.kotlin_myshare.bean.Constant
import com.jara.kotlin_myshare.bean.ShareEntity
import com.jara.kotlin_myshare.channel.*
import com.jara.kotlin_myshare.interfaces.OnShareListener
import com.tencent.connect.common.Constants
import com.tencent.tauth.Tencent

/**
 * Created by jara on 2017-9-15.
 */
class ShareHandlerActivity : ShareBaseActivity(), OnShareListener {

    var entity: ShareEntity? = null
    var shareByWeChat: ShareByWeChatK? = null
    var isInit = true

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        var obj: Any? = null
        try {
            obj = intent.getParcelableExtra(Constant.EXTRA_SHARE_DATA)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (null == obj || obj !is ShareEntity) {
            finish()
            return
        }
        entity = obj
        if (savedInstanceState == null) {
            if (null != shareByWeChat) {
                shareByWeChat!!.unregisterWeChatReceiver()
                shareByWeChat = null
            }
            when (channel) {
                Constant.SHARE_CHANNEL_WEIXIN_CIRCLE -> {
                    shareByWeChat = ShareByWeChatK(this, Constant.SHARE_CHANNEL_WEIXIN_CIRCLE)
                    shareByWeChat!!.registerWeChatReceiver()
                    shareByWeChat!!.share(entity!!, this)
                }
                Constant.SHARE_CHANNEL_WEIXIN_FRIEND -> {
                    shareByWeChat = ShareByWeChatK(this, Constant.SHARE_CHANNEL_WEIXIN_FRIEND)
                    shareByWeChat!!.registerWeChatReceiver()
                    shareByWeChat!!.share(entity!!, this)
                }
                Constant.SHARE_CHANNEL_QQ -> {
                    ShareByQQK(this).share(entity!!, this)
                }
                Constant.SHARE_CHANNEL_QZONE -> {
                    ShareByQZoneK(this).share(entity!!, this)
                }
                Constant.SHARE_CHANNEL_SMS -> {
                    ShareBySmsK(this).share(entity!!, this)
                }
                Constant.SHARE_CHANNEL_EMAIL -> {
                    ShareByEmailK(this).share(entity!!, this)
                }
                Constant.SHARE_CHANNEL_SYSTEM -> {
                    ShareBySystemK(this).share(entity!!, this)
                }
                else -> finishWithResult(channel!!, Constant.SHARE_STATUS_ERROR)
            }
        }
    }

    override fun onShare(channel: Int, status: Int) {
        finishWithResult(channel, status)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (!isInit) {
            finishWithResult(channel!!, Constant.SHARE_STATUS_ERROR)
        } else {
            isInit = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_QZONE_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, null)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (null != shareByWeChat) {
            shareByWeChat!!.unregisterWeChatReceiver()
        }
    }
}