package com.example.alfagift.api

import com.example.alfagift.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class OkHttpClientHelper {
    fun initOkHttpClient(): OkHttpClient {

        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        // Add logging for debug builds
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(logging)

            // Add Chuck interceptor
//            okHttpClientBuilder.addInterceptor(
//                ChuckerInterceptor.Builder(App.context)
//                    .collector(ChuckerCollector(App.context))
//                    .build()
//            )
        }

        // Set timeout duration
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }
}