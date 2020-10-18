package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.domain.AuthentificationToken
import com.realifetech.sdk.general.General
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class OAuth2AuthenticationInterceptor(private val accessToken: AuthentificationToken) : Interceptor {

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
