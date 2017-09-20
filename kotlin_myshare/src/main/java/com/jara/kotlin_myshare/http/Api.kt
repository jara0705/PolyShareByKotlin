package com.jara.kotlin_myshare.http

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by jara on 2017-9-20.
 */
interface Api {
    @GET
    fun getImage(@Url url: String) : Flowable<ResponseBody>
}