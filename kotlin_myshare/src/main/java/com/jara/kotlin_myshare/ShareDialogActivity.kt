package com.jara.kotlin_myshare

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseArray
import android.view.View
import com.jara.kotlin_myshare.adapters.ChannelRecyclerAdapter
import com.jara.kotlin_myshare.bean.Constant
import com.jara.kotlin_myshare.bean.ChannelEntity
import com.jara.kotlin_myshare.bean.ShareEntity
import com.jara.kotlin_myshare.interfaces.IMyItemclickListener
import com.jara.kotlin_myshare.utils.ShareUtil


class ShareDialogActivity : ShareBaseActivity() {

    var channelEntities: MutableList<ChannelEntity> = ArrayList()
    var entity: ShareEntity ?= null
    var sparseArray: SparseArray<ShareEntity> ?= null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_dialog)
        recyclerView = findViewById(R.id.share_recy)
        var objectAny: Any
        if (intent.hasExtra(Constant.EXTRA_SHARE_DATA)) {
            val bundle = intent.getBundleExtra(Constant.EXTRA_SHARE_DATA)
            if (null != bundle) {
                objectAny = bundle.get(Constant.EXTRA_SHARE_DATA)
            } else {
                objectAny = intent.getParcelableExtra(Constant.EXTRA_SHARE_DATA)
                if (objectAny == null) {
                    objectAny = intent.getSerializableExtra(Constant.EXTRA_SHARE_DATA)
                }
            }
        } else {
            objectAny = intent.data
        }
        if (objectAny == null) {
            finish()
            return
        }
        if (objectAny is ShareEntity) {
            entity = objectAny
        } else if (objectAny is SparseArray<*>) {
            try {
                sparseArray = objectAny as SparseArray<ShareEntity>
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        initChannelData()
        if (entity == null && sparseArray == null) {
            finish()
            return
        }
        initView()
    }

    fun initChannelData() {
        if (channel!! and Constant.SHARE_CHANNEL_WEIXIN_FRIEND > 0 && isShowChannel(Constant.SHARE_CHANNEL_WEIXIN_FRIEND)) {
            channelEntities.add(ChannelEntity(Constant.SHARE_CHANNEL_WEIXIN_FRIEND, R.drawable.ic_share_wechat, getString(R.string.share_channel_weixin_friend)))
        }
        if (channel!! and Constant.SHARE_CHANNEL_WEIXIN_CIRCLE > 0 && isShowChannel(Constant.SHARE_CHANNEL_WEIXIN_CIRCLE)) {
            channelEntities.add(ChannelEntity(Constant.SHARE_CHANNEL_WEIXIN_CIRCLE, R.drawable.ic_share_wxcircle, getString(R.string.share_channel_weixin_circle)))
        }

        /** QQ **/

        if (channel!! and Constant.SHARE_CHANNEL_QQ > 0 && isShowChannel(Constant.SHARE_CHANNEL_QQ)) {
            channelEntities.add(ChannelEntity(Constant.SHARE_CHANNEL_QQ, R.drawable.ic_share_qq, getString(R.string.share_channel_qq)))
        }
        if (channel!! and Constant.SHARE_CHANNEL_QZONE > 0 && isShowChannel(Constant.SHARE_CHANNEL_QZONE)) {
            channelEntities.add(ChannelEntity(Constant.SHARE_CHANNEL_QZONE, R.drawable.ic_share_qzone, getString(R.string.share_channel_qzone)))
        }

        if (channel!! and Constant.SHARE_CHANNEL_SMS > 0 && isShowChannel(Constant.SHARE_CHANNEL_SMS)) {
            channelEntities.add(ChannelEntity(Constant.SHARE_CHANNEL_SMS, R.drawable.ic_share_sms, getString(R.string.share_channel_sms)))
        }

        if (channel!! and Constant.SHARE_CHANNEL_EMAIL > 0 && isShowChannel(Constant.SHARE_CHANNEL_EMAIL)) {
            channelEntities.add(ChannelEntity(Constant.SHARE_CHANNEL_EMAIL, R.drawable.ic_share_email, getString(R.string.share_channel_email)))
        }

        if (channel!! and Constant.SHARE_CHANNEL_SYSTEM > 0 && isShowChannel(Constant.SHARE_CHANNEL_SYSTEM)) {
            channelEntities.add(ChannelEntity(Constant.SHARE_CHANNEL_SYSTEM, R.drawable.ic_share_system, getString(R.string.share_channel_system)))
        }

    }

    fun initView() {
        recyclerView = findViewById(R.id.share_recy)
        val gm = GridLayoutManager(this, 3)
        recyclerView.layoutManager = gm
        recyclerView.setHasFixedSize(true)
        var adapter = ChannelRecyclerAdapter(channelEntities!!)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object: IMyItemclickListener {
            override fun onItemClick(view: View, position: Int) {
                handleShare(channelEntities!![position].getchannel())
                Log.i("ShareDialogActivity", "取到的分享值---> ${channelEntities!![position].getchannel()}")
            }
        })
    }

    fun isShowChannel(channel: Int): Boolean {
        if (sparseArray != null) {
            if (sparseArray!![channel] != null) {
                return true
            }
            return false
        }
        return true
    }

    fun handleShare(channel: Int) {
        when(channel) {
            Constant.SHARE_CHANNEL_QQ -> shareByQQ()
            Constant.SHARE_CHANNEL_QZONE -> shareByQZone()
            Constant.SHARE_CHANNEL_WEIXIN_FRIEND -> shareByWeixinFriend()
            Constant.SHARE_CHANNEL_WEIXIN_CIRCLE -> shareByWeixinCircle()
            Constant.SHARE_CHANNEL_SYSTEM -> shareBySystem()
            Constant.SHARE_CHANNEL_SMS -> shareBySms()
            Constant.SHARE_CHANNEL_EMAIL -> shareByEmail()
            else -> return
        }
    }

    fun shareByQQ() {
        ShareUtil.startShare(this, Constant.SHARE_CHANNEL_QQ, getShareData(Constant.SHARE_CHANNEL_QQ), Constant.REQUEST_CODE);
    }

    fun shareByQZone() {
        ShareUtil.startShare(this, Constant.SHARE_CHANNEL_QZONE, getShareData(Constant.SHARE_CHANNEL_QZONE), Constant.REQUEST_CODE);
    }

    fun shareByWeixinFriend() {
        ShareUtil.startShare(this, Constant.SHARE_CHANNEL_WEIXIN_FRIEND, getShareData(Constant.SHARE_CHANNEL_WEIXIN_FRIEND), Constant.REQUEST_CODE);
    }

    fun shareByWeixinCircle() {
        ShareUtil.startShare(this, Constant.SHARE_CHANNEL_WEIXIN_CIRCLE, getShareData(Constant.SHARE_CHANNEL_WEIXIN_CIRCLE), Constant.REQUEST_CODE);
    }

    fun shareBySms() {
        ShareUtil.startShare(this, Constant.SHARE_CHANNEL_SMS, getShareData(Constant.SHARE_CHANNEL_SMS), Constant.REQUEST_CODE);
    }

    fun shareByEmail() {
        ShareUtil.startShare(this, Constant.SHARE_CHANNEL_EMAIL, getShareData(Constant.SHARE_CHANNEL_EMAIL), Constant.REQUEST_CODE);
    }

    fun shareBySystem() {
        ShareUtil.startShare(this, Constant.SHARE_CHANNEL_SYSTEM, getShareData(Constant.SHARE_CHANNEL_SYSTEM), Constant.REQUEST_CODE);
    }

    fun getShareData(shareChannel: Int): ShareEntity? {
        if (entity != null) {
            return entity as ShareEntity
        }
        if (sparseArray != null) {
            return sparseArray!!.get(shareChannel)
        }
        return null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE) {
            if (data != null) {
                val channel = data.getIntExtra(Constant.EXTRA_SHARE_CHANNEL, -1)
                val status = data.getIntExtra(Constant.EXTRA_SHARE_STATUS, -1)
                finishWithResult(channel, status)
                return
            }
        }
    }
}
