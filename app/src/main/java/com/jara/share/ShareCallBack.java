package com.jara.share;


import com.jara.kotlin_myshare.bean.Constant;

/**
 * Created by jara on 2017-9-5.
 */

public class ShareCallBack {

    public void onShareCallback(int channel, int status) {
        switch (channel) {
            case Constant.SHARE_CHANNEL_WEIXIN_FRIEND:
            case Constant.SHARE_CHANNEL_WEIXIN_CIRCLE:
                onWeixinCallBack(status);
                break;

            case Constant.SHARE_CHANNEL_SINA_WEIBO:
                onWeiboCallBack(status);
                break;

            case Constant.SHARE_CHANNEL_QQ:
                onQQCallBack(status);
                break;

            case Constant.SHARE_CHANNEL_QZONE:
                onQZoneCallBack(status);
                break;

            case Constant.SHARE_CHANNEL_SYSTEM:
                onSystemCallBack(status);
                break;
        }
    }


    private void onWeixinCallBack(int status) {
        switch (status) {
            case Constant.SHARE_STATUS_COMPLETE:

                break;
            case Constant.SHARE_STATUS_FAILED:

                break;
        }
    }

    private void onWeiboCallBack(int status) {
        switch (status) {
            /** 成功 **/
            case Constant.SHARE_STATUS_COMPLETE:

                break;
            /** 失败 **/
            case Constant.SHARE_STATUS_FAILED:

                break;
            /** 取消 **/
            case Constant.SHARE_STATUS_CANCEL:

                break;
        }
    }

    private void onQQCallBack(int status) {
        switch (status) {
            /** 成功 **/
            case Constant.SHARE_STATUS_COMPLETE:

                break;
            /** 失败 **/
            case Constant.SHARE_STATUS_FAILED:

                break;
            /** 取消 **/
            case Constant.SHARE_STATUS_CANCEL:

                break;
        }
    }

    private void onQZoneCallBack(int status) {
        switch (status) {
            /** 成功 **/
            case Constant.SHARE_STATUS_COMPLETE:

                break;
            /** 失败 **/
            case Constant.SHARE_STATUS_FAILED:

                break;
            /** 取消 **/
            case Constant.SHARE_STATUS_CANCEL:

                break;
        }
    }

    private void onSystemCallBack(int status) {
    }

}
