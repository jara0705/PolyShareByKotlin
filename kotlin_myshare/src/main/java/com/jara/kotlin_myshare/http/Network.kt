package com.jara.kotlin_myshare.http

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit

/**
 * Created by jara on 2017-9-20.
 */
object Network {
    @JvmField
    var instance : Retrofit ?= null
    @JvmField
    var service: Api ?= null

    init {
        initService()
    }

    @JvmStatic
    fun getService(): Api? {
        if (service == null) {
            initService()
        }
        return service
    }

    fun initService() {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://www.baidu.com")
                .client(OKHttpClientFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        service = retrofit.create(Api::class.java)
    }
}