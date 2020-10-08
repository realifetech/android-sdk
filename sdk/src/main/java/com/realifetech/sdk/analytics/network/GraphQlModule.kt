package com.realifetech.sdk.analytics.network

import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.Realifetech
import okhttp3.OkHttpClient

internal object GraphQlModule {
    val apolloClient: ApolloClient
        get() = ApolloClient.builder()
            .serverUrl(Realifetech.getGeneral().configuration.graphApiUrl)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(AnalyticsHeadersInterceptor())
                    .build()
            )
            .build()
}