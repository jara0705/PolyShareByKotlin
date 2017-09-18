package com.jara.kotlin_myshare.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jara.kotlin_myshare.R
import com.jara.kotlin_myshare.bean.ChannelEntity
import com.jara.kotlin_myshare.interfaces.IMyItemclickListener

/**
 * Created by jara on 2017-9-15.
 */
class ChannelRecyclerAdapter(var channelEntities: MutableList<ChannelEntity>): RecyclerView.Adapter<ChannelRecyclerAdapter.ViewHolder>()
        , View.OnClickListener{

    var itemClickListener: IMyItemclickListener ?= null

    override fun onClick(view: View?) {
        if (itemClickListener != null) {
            itemClickListener!!.onItemClick(view!!, view.tag as Int)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder!!.textView.text = channelEntities[position].name
        holder!!.imageView.setImageResource(channelEntities[position].icon)
        holder.itemView.tag = position
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.share_gridview_item, parent, false)
        var vh = ViewHolder(view)
        view.setOnClickListener(this)
        return vh
    }

    override fun getItemCount(): Int {
        return channelEntities.size
    }

    fun setOnItemClickListener(listener: IMyItemclickListener) {
        itemClickListener = listener
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView!!.findViewById(R.id.image)
        val textView: TextView = itemView!!.findViewById(R.id.text)
    }
}