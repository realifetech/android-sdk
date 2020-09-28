package com.realifetech.sdk.general.network

import android.util.Log
import com.moczul.ok2curl.CurlInterceptor
import com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor
import com.realifetech.sdk.general.General
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object DeviceApiNetwork {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(OAuth2AuthenticationInterceptor())
        .addInterceptor(CurlInterceptor { message ->
            Log.d("SdkCurl", message)
        })
        .build()

    fun get(): DeviceApiService {
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(General.instance.configuration.apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(DeviceApiService::class.java)
    }
}