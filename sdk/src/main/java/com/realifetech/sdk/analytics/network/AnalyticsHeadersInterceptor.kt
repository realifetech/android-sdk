package com.realifetech.sdk.analytics.network

import com.realifetech.sdk.Realifetech
import com.realifetech.sdk.general.General
import okhttp3.Interceptor
import okhttp3.Response

internal class AnalyticsHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val clientSecret = General.instance.configuration.clientSecret

        val request = chain.request().newBuilder()
            .addHeader("X-LS-DeviceId", Realifetech.getGeneral().deviceIdentifier)
            .addHeader("Authorization", "Bearer $clientSecret")
            .build()

        return chain.proceed(request)
    }
}