package com.jara.kotlin_myshare.interfaces

import android.view.View

/**
 * RecyclerView点击事件接口
 * Created by jara on 2017/9/17.
 */
interface IMyItemclickListener {
    fun onItemClick(view: View, position: Int)
}