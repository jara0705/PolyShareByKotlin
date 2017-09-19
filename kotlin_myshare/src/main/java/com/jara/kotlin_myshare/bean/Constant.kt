package com.jara.kotlin_myshare.bean

/**
 * 全局静态常量 用object代替class 常量使用@JVMField注解
 * Created by jara on 2017-9-4.
 */

object Constant {


    /// -------------------------- 分享渠道 --------------------------
    /**
     * 微信好友分享渠道
     */
    @JvmField
    val SHARE_CHANNEL_WEIXIN_FRIEND = 1 //微信好友
    /**
     * weixin circle
     */
    @JvmField
    val SHARE_CHANNEL_WEIXIN_CIRCLE = 1 shl 1
    /**
     * 新浪微博渠道
     */
    @JvmField
    val SHARE_CHANNEL_SINA_WEIBO = 1 shl 2 //新浪微博
    /**
     * QQ分享渠道
     */
    @JvmField
    val SHARE_CHANNEL_QQ = 1 shl 3 //QQ
    /**
     * QQ空间渠道
     */
    @JvmField
    val SHARE_CHANNEL_QZONE = 1 shl 4//qzone分享
    /**
     * 短信分享渠道
     */
    @JvmField
    val SHARE_CHANNEL_SMS = 1 shl 5 //短信
    /**
     * 邮箱分享渠道
     */
    @JvmField
    val SHARE_CHANNEL_EMAIL = 1 shl 6 //邮箱
    /**
     * 更多渠道
     */
    @JvmField
    val SHARE_CHANNEL_SYSTEM = 1 shl 10 //更多
    /**
     * All channel
     */
    @JvmField
    val SHARE_CHANNEL_ALL = 0.inv()

    /// -------------------------- 分享渠道 --------------------------

    /// <<<< share status
    @JvmField
    val EXTRA_SHARE_DATA = "extra_share_data"
    @JvmField
    val EXTRA_SHARE_CHANNEL = "extra_show_channel"
    @JvmField
    val EXTRA_SHARE_STATUS = "extra_share_status"
    @JvmField
    val SHARE_STATUS_COMPLETE = 1
    @JvmField
    val SHARE_STATUS_FAILED = 2
    @JvmField
    val SHARE_STATUS_CANCEL = 3
    @JvmField
    val SHARE_STATUS_ERROR = 4

    /// <<<< share status


    /// <<< 微信回调
    @JvmField
    val ACTION_WEIXIN_CALLBACK = "com.jara.share.action.WEIXIN_CALLBACK"
    @JvmField
    val EXTRA_WEIXIN_RESULT = "weixin_result"
    /// <<< 微信回调
    @JvmField
    val WEIXIN_PACKAGE_NAME = "com.tencent.mm" // 微信包名
    @JvmField
    val QQ_PACKAGE_NAME = "com.tencent.mobileqq" // QQ包名

    /// <<<< request code
    @JvmField
    val REQUEST_CODE = 20112
    /// <<<< request code

}
