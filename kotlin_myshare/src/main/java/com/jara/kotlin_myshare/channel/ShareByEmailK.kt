package com.jara.kotlin_myshare.channel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.jara.kotlin_myshare.bean.Constant
import com.jara.kotlin_myshare.bean.ShareEntity
import com.jara.kotlin_myshare.interfaces.OnShareListener
import com.jara.kotlin_myshare.utils.ShareUtil
/**
 * Created by Administrator on 2017-9-19.
 */
class ShareByEmailK(context: Context) : ShareBaseK(context) {
    override fun share(entity: ShareEntity, listener: OnShareListener) {
        if (entity == null || TextUtils.isEmpty(entity.content)) {
            return
        }
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        if (!TextUtils.isEmpty(entity.title)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, entity.title)
        }
        val content = entity.content + entity.url
        intent.putExtra(Intent.EXTRA_TEXT, content)
        if (ShareUtil.startActivity(context, intent)) {
            if (null != listener) {
                listener.onShare(Constant.SHARE_CHANNEL_EMAIL, Constant.SHARE_STATUS_COMPLETE)
            }
        } else {
            if (null != listener) {
                listener.onShare(Constant.SHARE_CHANNEL_EMAIL, Constant.SHARE_STATUS_FAILED)
            }
        }
    }
}