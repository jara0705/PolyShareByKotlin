package com.jara.kotlin_myshare.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import com.jara.kotlin_myshare.ShareDialogActivity
import com.jara.kotlin_myshare.ShareHandlerActivity
import com.jara.kotlin_myshare.bean.Constant
import com.jara.kotlin_myshare.bean.ShareEntity
/**
 * Created by jara on 2017-9-15.
 */
object ShareUtil {
    fun startShare(activity: Activity, channel: Int, entity: ShareEntity?, requestCode: Int) {
        if (null == activity || activity.isFinishing) {
            return
        }
        val intent = Intent(activity, ShareHandlerActivity::class.java)
        intent.putExtra(Constant.EXTRA_SHARE_CHANNEL, channel)
        intent.putExtra(Constant.EXTRA_SHARE_DATA, entity)
        activity.startActivityForResult(intent, requestCode)
    }
    fun showShareDialog(activity: Activity, entity: ShareEntity?, requestCode: Int) {
        showShareDialog(activity, Constant.SHARE_CHANNEL_ALL, entity, requestCode)
    }

    fun showShareDialog(activity: Activity, channel: Int = Constant.SHARE_CHANNEL_ALL, entity: ShareEntity?, requestCode: Int) {
        if (null == activity || activity.isFinishing) {
            return
        }
        val intent = Intent(activity, ShareDialogActivity::class.java)
        intent.putExtra(Constant.EXTRA_SHARE_DATA, entity)
        intent.putExtra(Constant.EXTRA_SHARE_CHANNEL, channel)
        activity.startActivityForResult(intent, requestCode)
    }
    fun showShareDialog(activity: Activity, data: SparseArray<ShareEntity>?, requestCode: Int) {

    }

    fun showShareDialog(activity: Activity, channel: Int = Constant.SHARE_CHANNEL_ALL, data: SparseArray<ShareEntity>?, requestCode: Int) {
        if (null == activity || activity.isFinishing) {
            return
        }
        val intent = Intent(activity, ShareDialogActivity::class.java)
        val bundle = Bundle()
        bundle.putSparseParcelableArray(Constant.EXTRA_SHARE_DATA, data)
        intent.putExtra(Constant.EXTRA_SHARE_DATA, bundle)
        intent.putExtra(Constant.EXTRA_SHARE_CHANNEL, channel)
        activity.startActivityForResult(intent, requestCode)
    }

    fun startActivity(context: Context, intent: Intent): Boolean {
        var bResult: Boolean = true
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            bResult = false
            e.printStackTrace()
        } catch (e: Exception) {
            bResult = false
            e.printStackTrace()
        }
        return bResult
    }

}