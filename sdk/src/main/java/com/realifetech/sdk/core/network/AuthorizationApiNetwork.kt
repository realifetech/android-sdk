package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.database.configuration.ConfigurationStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthorizationApiNetwork(private val configurationStorage: ConfigurationStorage) {

    private val httpClient: OkHttpClient
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

    fun get(): RealifetechApiV3Service {
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(configurationStorage.apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RealifetechApiV3Service::class.java)
    }
}