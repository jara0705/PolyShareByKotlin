package com.jara.kotlin_myshare.channel

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import com.jara.kotlin_myshare.bean.Constant
import com.jara.kotlin_myshare.bean.ShareEntity
import com.jara.kotlin_myshare.interfaces.OnShareListener
import com.tencent.connect.share.QzoneShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError

/**
 * Created by Jara on 2017/9/17.
 */
class ShareByQZoneK(context: Context): ShareByQQK(context) {

    override fun share(entity: ShareEntity, listener: OnShareListener) {
        super.share(entity, listener)

        if (null == entity) {
            return
        }
        if (null == context || context !is Activity) {
            return
        }
        val params = Bundle()
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT)
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, entity.title)
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, entity.content)
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, entity.url)
        val arrayList: MutableList<String> = ArrayList()
        if (!TextUtils.isEmpty(entity.imgUrl)) {
            arrayList.add(entity.imgUrl!!)
        }
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, arrayList as java.util.ArrayList<String>?)
        tencent.shareToQzone(context as Activity, params, object : IUiListener{
            override fun onComplete(p0: Any?) {
                if (null != listener) {
                    listener.onShare(Constant.SHARE_CHANNEL_QZONE, Constant.SHARE_STATUS_COMPLETE)
                }
            }

            override fun onCancel() {
                if (null != listener) {
                    listener.onShare(Constant.SHARE_CHANNEL_QZONE, Constant.SHARE_STATUS_CANCEL)
                }
            }

            override fun onError(p0: UiError?) {
                if (null != listener) {
                    listener.onShare(Constant.SHARE_CHANNEL_QZONE, Constant.SHARE_STATUS_FAILED)
                }
            }

        })
    }

}