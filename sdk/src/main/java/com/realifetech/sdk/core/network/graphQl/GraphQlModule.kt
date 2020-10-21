package com.realifetech.sdk.core.network.graphQl

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.moczul.ok2curl.CurlInterceptor
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.di.CoreProvider
import okhttp3.OkHttpClient

internal object GraphQlModule {
    val apolloClient: ApolloClient
        get() = ApolloClient.builder()
            .serverUrl(RealifeTech.getGeneral().configuration.graphApiUrl)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(CurlInterceptor { message ->
                        Log.d("SdkCurl", message)
                    })
                    .addInterceptor(CoreProvider.graphQlInterceptor)
                    .build()
            )
            .build()
}