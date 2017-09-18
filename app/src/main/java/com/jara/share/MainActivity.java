package com.jara.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jara.kotlin_myshare.bean.Constant;
import com.jara.kotlin_myshare.bean.ShareEntity;
import com.jara.kotlin_myshare.utils.ShareUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ShareEntity entity;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entity = new ShareEntity("我是标题", "我是内容，描述内容。");
        entity.setUrl("https://www.baidu.com");
        entity.setImgUrl("https://www.baidu.com/img/bd_logo1.png");
        findViewById(R.id.show_share).setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.image);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.show_share:
                ShareUtil.INSTANCE.showShareDialog(this, entity, Constant.REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_CODE) {
            if (data != null) {
                int channel = data.getIntExtra(Constant.EXTRA_SHARE_CHANNEL, -1);
                int status = data.getIntExtra(Constant.EXTRA_SHARE_STATUS, -1);
                onShareCallback(channel, status);
            }
        }
    }

    private void onShareCallback(int channel, int status) {
        new ShareCallBack().onShareCallback(channel, status);
    }
}
