package com.jara.kotlin_myshare.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Administrator on 2017-9-15.
 */

class ShareEntity(var title: String, var content: String) : Parcelable {

    var url: String? = null
    var imgUrl: String? = null
    var drawableId: Int = 0
    var isShareBigImg: Boolean = false

    constructor(title:String, content:String, url: String):this(title, content){
        this.url = url
    }

    constructor(title:String, content:String, url: String, imgUrl: String):this(title, content, url){
        this.url = url
        this.imgUrl = imgUrl
    }

    protected constructor(parcel: Parcel): this(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString()){
        drawableId = parcel.readInt()
        isShareBigImg = parcel.readByte().toInt() != 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(url)
        parcel.writeString(imgUrl)
        parcel.writeInt(drawableId)
        parcel.writeByte((if (isShareBigImg) 1 else 0).toByte())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShareEntity> {
        override fun createFromParcel(parcel: Parcel): ShareEntity {
            return ShareEntity(parcel)
        }

        override fun newArray(size: Int): Array<ShareEntity?> {
            return arrayOfNulls(size)
        }
    }

}
