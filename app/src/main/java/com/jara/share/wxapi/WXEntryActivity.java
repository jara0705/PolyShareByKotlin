package com.jara.share.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jara.kotlin_myshare.bean.Constant;
import com.jara.kotlin_myshare.utils.ManifestUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by jara on 2017-9-20.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

    private IWXAPI iwxapi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi = WXAPIFactory.createWXAPI(this, ManifestUtil.getWeixinKey(this), false);
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Intent intent = new Intent();
        intent.setAction(Constant.ACTION_WEIXIN_CALLBACK);
        intent.putExtra(Constant.EXTRA_WEIXIN_RESULT, baseResp.errCode);
        sendBroadcast(intent);
        finish();
    }
}
