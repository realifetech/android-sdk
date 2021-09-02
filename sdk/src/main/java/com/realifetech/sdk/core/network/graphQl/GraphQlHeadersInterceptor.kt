package com.realifetech.sdk.core.network.graphQl

import android.util.Log
import com.realifetech.sdk.core.data.shared.`object`.toBearerFormat
import com.realifetech.sdk.core.domain.AuthenticationToken
import com.realifetech.sdk.core.domain.RLTConfiguration
import okhttp3.Interceptor
import okhttp3.Response

class GraphQlHeadersInterceptor(private val accessToken: AuthenticationToken) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        accessToken.ensureActive()
        val token = accessToken.accessToken
        Log.d("GraphQlInterceptor", "Sending request with auth token: $token")

        val request = chain.request().newBuilder()
            .addHeader("X-LS-DeviceId", RLTConfiguration.DEVICE_ID)
            .addHeader("Authorization", token.toBearerFormat)
            .build()

        return chain.proceed(request)
    }
}