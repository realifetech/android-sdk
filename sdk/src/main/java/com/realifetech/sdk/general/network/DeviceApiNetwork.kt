package com.realifetech.sdk.general.network

import com.realifetech.sdk.core.di.CoreProvider
import com.realifetech.sdk.general.General
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object DeviceApiNetwork {

    private val httpClient: OkHttpClient
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .addInterceptor(CoreProvider.oAuthInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        }

    fun get(): DeviceApiService {
        var baseUrl = General.instance.configuration.apiUrl
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/"
        }
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(DeviceApiService::class.java)
    }
}