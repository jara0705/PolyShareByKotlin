package com.jara.kotlin_myshare.channel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.jara.kotlin_myshare.bean.Constant
import com.jara.kotlin_myshare.bean.ShareEntity
import com.jara.kotlin_myshare.interfaces.OnShareListener
import com.jara.kotlin_myshare.utils.ShareUtil
import com.jara.myshare.channel.ShareBaseK

/**
 * Created by jara on 2017-9-19.
 */
class ShareBySmsK(context: Context) : ShareBaseK(context) {

    override fun share(entity: ShareEntity, listener: OnShareListener) {
        if (entity == null || TextUtils.isEmpty(entity.content)) {
            return
        }
        var content: String = entity.content + entity.url
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("smsto:"))
        intent.putExtra("sms_body", content)
        intent.type = "vnd.android-dir/mms-sms"
        if (ShareUtil.startActivity(context, intent)) {
            if (null != listener) {
                listener.onShare(Constant.SHARE_CHANNEL_SMS, Constant.SHARE_STATUS_COMPLETE)
            }
        } else {
            if (null != listener) {
                listener.onShare(Constant.SHARE_CHANNEL_SMS, Constant.SHARE_STATUS_FAILED)
            }
        }
    }
}