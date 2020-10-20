package com.realifetech.sdk.analytics.network

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.Logger
import com.realifetech.sdk.Realifetech
import com.realifetech.sdk.analytics.di.AnalyticsProvider
import okhttp3.OkHttpClient

internal object GraphQlModule {
    val apolloClient: ApolloClient
        get() = ApolloClient.builder()
            .serverUrl(Realifetech.getGeneral().configuration.graphApiUrl)
            .logger(object : Logger {
                override fun log(priority: Int, message: String, t: Throwable?, vararg args: Any) {
                    Log.d("ApolloClient", "$priority : $message", t)
                }
            })
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(AnalyticsProvider.headerInterceptor)
                    .build()
            )
            .build()
}