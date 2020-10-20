package com.realifetech.sdk.analytics.network

import com.realifetech.sdk.Realifetech
import com.realifetech.sdk.core.domain.AuthenticationToken
import com.realifetech.sdk.general.General
import okhttp3.Interceptor
import okhttp3.Response

internal class AnalyticsHeadersInterceptor(private val accessToken: AuthenticationToken) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        accessToken.ensureActive()
        val token = accessToken.accessToken

        val request = chain.request().newBuilder()
            .addHeader("X-LS-DeviceId", Realifetech.getGeneral().deviceIdentifier)
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}