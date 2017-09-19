package com.jara.kotlin_myshare.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils

/**
 * Created by jara on 2017-9-19.
 */
object ManifestUtil {
    @JvmField
    val WEIXIN_KEY = "weixin_key"
    @JvmField
    val WEIXIN_REDIRECTURI_KEY = "weixin_redirecturi"
    @JvmField
    val TENCENT_QQ_APPID = "tencent_qq_appid"

    @JvmStatic
    fun getWeixinKey(context: Context): String? {
        try {
            val appInfo = context.packageManager.getApplicationInfo(context.packageName
                    , PackageManager.GET_META_DATA)
            if (null != appInfo) {
                val bundle = appInfo.metaData
                var key: String ?= null
                if (null != bundle) {
                    key = bundle.get(WEIXIN_KEY) as String?
                }
                if (!TextUtils.isEmpty(key)) {
                    return key
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    @JvmStatic
    fun getTencentQQAppId(context: Context): String? {
        try {
            val appInfo = context.packageManager.getApplicationInfo(context.packageName
                    , PackageManager.GET_META_DATA)
            if (null != appInfo) {
                val bundle = appInfo.metaData
                var key: String ?= null
                if (null != bundle) {
                    key = bundle.get(TENCENT_QQ_APPID) as String?
                }
                if (!TextUtils.isEmpty(key)) {
                    return key
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}