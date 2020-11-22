package com.realifetech.core_sdk.network.graphQl

import com.apollographql.apollo.ApolloClient
import com.realifetech.core_sdk.di.CoreProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object GraphQlModule {
    fun getApolloClient(serverUrl: String): ApolloClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return ApolloClient.builder()
            .serverUrl(serverUrl)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(CoreProvider.graphQlInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .build()
    }
}