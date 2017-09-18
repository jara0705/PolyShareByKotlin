//package com.jara.kotlin_myshare.bean
//
///**
// * Created by jara on 2017-9-4.
// */
//
//class Constant {
//    companion object {
//
//
//        /// -------------------------- 分享渠道 --------------------------
//        /**
//         * 微信好友分享渠道
//         */
//        val SHARE_CHANNEL_WEIXIN_FRIEND = 1 //微信好友
//        /**
//         * weixin circle
//         */
//        val SHARE_CHANNEL_WEIXIN_CIRCLE = 1 shl 1
//        /**
//         * 新浪微博渠道
//         */
//        val SHARE_CHANNEL_SINA_WEIBO = 1 shl 2 //新浪微博
//        /**
//         * QQ分享渠道
//         */
//        val SHARE_CHANNEL_QQ = 1 shl 3 //QQ
//        /**
//         * QQ空间渠道
//         */
//        val SHARE_CHANNEL_QZONE = 1 shl 4//qzone分享
//        /**
//         * 短信分享渠道
//         */
//        val SHARE_CHANNEL_SMS = 1 shl 5 //短信
//        /**
//         * 邮箱分享渠道
//         */
//        val SHARE_CHANNEL_EMAIL = 1 shl 6 //邮箱
//        /**
//         * 更多渠道
//         */
//        val SHARE_CHANNEL_SYSTEM = 1 shl 10 //更多
//        /**
//         * All channel
//         */
//        val SHARE_CHANNEL_ALL = 0.inv()
//
//        /// -------------------------- 分享渠道 --------------------------
//
//        /// <<<< share status
//        val EXTRA_SHARE_DATA = "extra_share_data"
//        val EXTRA_SHARE_CHANNEL = "extra_show_channel"
//        val EXTRA_SHARE_STATUS = "extra_share_status"
//
//        val SHARE_STATUS_COMPLETE = 1
//        val SHARE_STATUS_FAILED = 2
//        val SHARE_STATUS_CANCEL = 3
//        val SHARE_STATUS_ERROR = 4
//
//        /// <<<< share status
//
//
//        /// <<< 微信回调
//        val ACTION_WEIXIN_CALLBACK = "com.jara.share.action.WEIXIN_CALLBACK"
//        val EXTRA_WEIXIN_RESULT = "weixin_result"
//        /// <<< 微信回调
//
//        val WEIXIN_PACKAGE_NAME = "com.tencent.mm" // 微信包名
//        val QQ_PACKAGE_NAME = "com.tencent.mobileqq" // QQ包名
//        val SINA_WEIBO_PACKAGE_NAME = "com.sina.weibo" // 新浪微博
//        val SINA_WEIBO_LITE_PACKAGE_NAME = "com.sina.weibolite"
//
//        /// <<<< request code
//        val REQUEST_CODE = 20112
//        /// <<<< request code
//    }
//}
