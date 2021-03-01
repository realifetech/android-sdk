package com.realifetech.core_sdk.network

import com.realifetech.core_sdk.domain.AuthenticationToken
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class OAuth2AuthenticationInterceptor(private val accessToken: AuthenticationToken) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()

        accessToken.ensureActive()
        val token = accessToken.accessToken
        if (token.isNotEmpty()) {
            newBuilder.header(AUTHORIZATION, "Bearer $token")
        }

        return chain.proceed(newBuilder.build())
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}