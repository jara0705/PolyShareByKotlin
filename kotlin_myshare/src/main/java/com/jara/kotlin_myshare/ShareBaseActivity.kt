package com.jara.kotlin_myshare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.jara.kotlin_myshare.bean.Constant

/**
 * Created by jara on 2017-9-15.
 */
abstract class ShareBaseActivity: FragmentActivity() {

    var channel: Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent == null) {
            finish()
            return
        }
        initChannel()
    }

    fun initChannel() {
        try {
            channel = intent.getIntExtra(Constant.EXTRA_SHARE_CHANNEL, Constant.SHARE_CHANNEL_ALL)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun finishWithResult(channel: Int, status: Int) {
        val intent = Intent()
        intent.putExtra(Constant.EXTRA_SHARE_CHANNEL, channel)
        intent.putExtra(Constant.EXTRA_SHARE_STATUS, status)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}