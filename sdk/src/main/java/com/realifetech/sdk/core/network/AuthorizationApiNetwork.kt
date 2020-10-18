package com.realifetech.sdk.core.network

import android.util.Log
import com.moczul.ok2curl.CurlInterceptor
import com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor
import com.realifetech.sdk.general.General
import com.realifetech.sdk.general.network.DeviceApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object AuthorizationApiNetwork {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(CurlInterceptor { message ->
            Log.d("SdkCurl", message)
        })
        .build()

    fun get(): AuthorizationApiService {
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(General.instance.configuration.apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AuthorizationApiService::class.java)
    }
}