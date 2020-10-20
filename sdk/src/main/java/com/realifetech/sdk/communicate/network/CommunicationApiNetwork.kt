package com.realifetech.sdk.communicate.network

import android.util.Log
import com.moczul.ok2curl.CurlInterceptor
import com.realifetech.sdk.core.di.CoreProvider
import com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor
import com.realifetech.sdk.general.General
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object CommunicationApiNetwork {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(CoreProvider.oAuthInterceptor)
        .addInterceptor(CurlInterceptor { message ->
            Log.d("SdkCurl", message)
        })
        .build()

    fun get(): CommunicationApiService {
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(General.instance.configuration.apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CommunicationApiService::class.java)
    }
}