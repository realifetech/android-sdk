package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.domain.CoreConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object AuthorizationApiNetwork {

    private val httpClient: OkHttpClient
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

    fun get(): AuthorizationApiService {
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(CoreConfiguration.finalApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(AuthorizationApiService::class.java)
    }
}