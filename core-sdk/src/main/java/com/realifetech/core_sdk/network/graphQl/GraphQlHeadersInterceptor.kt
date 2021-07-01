package com.realifetech.core_sdk.network.graphQl

import android.util.Log
import com.realifetech.core_sdk.data.shared.`object`.toBearerFormat
import com.realifetech.core_sdk.domain.AuthenticationToken
import com.realifetech.core_sdk.domain.CoreConfiguration
import okhttp3.Interceptor
import okhttp3.Response

class GraphQlHeadersInterceptor(private val accessToken: AuthenticationToken) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        accessToken.ensureActive()
        val token = accessToken.accessToken
        Log.d("GraphQlInterceptor", "Sending request with auth token: $token")

        val request = chain.request().newBuilder()
            .addHeader("X-LS-DeviceId", CoreConfiguration.deviceId)
            .addHeader("Authorization", token.toBearerFormat)
            .build()

        return chain.proceed(request)
    }
}