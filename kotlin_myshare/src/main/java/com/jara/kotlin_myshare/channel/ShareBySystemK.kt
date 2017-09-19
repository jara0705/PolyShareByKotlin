package com.jara.kotlin_myshare.channel

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.jara.kotlin_myshare.R
import com.jara.kotlin_myshare.bean.Constant
import com.jara.kotlin_myshare.bean.ShareEntity
import com.jara.kotlin_myshare.interfaces.OnShareListener
import com.jara.kotlin_myshare.utils.ShareUtil
import com.jara.myshare.channel.ShareBaseK

/**
 * Created by jara on 2017-9-19.
 */
class ShareBySystemK(context: Context) : ShareBaseK(context) {

    override fun share(entity: ShareEntity, listener: OnShareListener) {
        if (entity == null || TextUtils.isEmpty(entity.content)) {
            return
        }
        val content = if (TextUtils.isEmpty(entity.content))
            entity.title + entity.url
        else entity.content + entity.url
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, content)
        intent.type = "text/plain"
        if (ShareUtil.startActivity(context
                , Intent.createChooser(intent, context.getString(R.string.share_to)))) {
            if (null != listener) {
                listener.onShare(Constant.SHARE_CHANNEL_SYSTEM, Constant.SHARE_STATUS_COMPLETE)
            }
        } else {
            if (null != listener) {
                listener.onShare(Constant.SHARE_CHANNEL_SYSTEM, Constant.SHARE_STATUS_FAILED)
            }
        }
    }
}