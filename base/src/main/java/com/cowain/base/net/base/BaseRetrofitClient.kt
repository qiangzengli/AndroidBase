package com.cowain.base.net.base

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseRetrofitClient {
    companion object CLIENT {
        private const val TIME_OUT = 20
    }

    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        handleBuilder(builder)
        builder.build()
    }

    private fun getHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    abstract fun handleBuilder(builder: OkHttpClient.Builder)

    open fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}
