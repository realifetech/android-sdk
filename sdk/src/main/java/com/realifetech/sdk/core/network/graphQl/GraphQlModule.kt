package com.realifetech.sdk.core.network.graphQl

import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.di.CoreProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

internal object GraphQlModule {
    val apolloClient: ApolloClient
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return ApolloClient.builder()
                .serverUrl(RealifeTech.getGeneral().configuration.graphApiUrl)
                .okHttpClient(
                    OkHttpClient.Builder()
                        .addInterceptor(CoreProvider.graphQlInterceptor)
                        .addInterceptor(loggingInterceptor)
                        .build()
                )
                .build()
        }
}