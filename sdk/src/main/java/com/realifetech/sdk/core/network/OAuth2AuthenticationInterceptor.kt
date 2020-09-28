package com.realifetech.sdk.core.network

import com.realifetech.sdk.general.General
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class OAuth2AuthenticationInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()

        val clientSecret = General.instance.configuration.clientSecret
        if (clientSecret.isNotEmpty()) {
            newBuilder.header(AUTHORIZATION, "Bearer $clientSecret")
        }

        return chain.proceed(newBuilder.build())
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}
