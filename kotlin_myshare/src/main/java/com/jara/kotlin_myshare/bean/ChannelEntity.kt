package com.jara.kotlin_myshare.bean

/**
 * Created by jara on 2017-9-4.
 */

class ChannelEntity(private var channel: Int, var icon: Int, var name: String?) {

    fun getchannel(): Int {
        return channel
    }

    fun setchannel(channel: Int) {
        this.channel = channel
    }
}
