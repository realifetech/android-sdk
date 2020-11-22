package com.realifetech.sdk.communicate.network

import com.realifetech.core_sdk.di.CoreProvider
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.general.General
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object CommunicationApiNetwork {

    private val httpClient: OkHttpClient
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(CoreProvider.oAuthInterceptor)
                .addInterceptor(CoreProvider.getDeviceIdInterceptor(RealifeTech.getGeneral().deviceIdentifier))
                .addInterceptor(loggingInterceptor)
                .build()
        }

    fun get(): CommunicationApiService {
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(General.instance.configuration.finalApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CommunicationApiService::class.java)
    }
}