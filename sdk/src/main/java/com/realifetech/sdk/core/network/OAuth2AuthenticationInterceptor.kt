package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.data.shared.`object`.toBearerFormat
import com.realifetech.sdk.core.domain.AuthenticationToken
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class OAuth2AuthenticationInterceptor @Inject constructor(private val accessToken: AuthenticationToken) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()

        accessToken.ensureActive()
        val token = accessToken.accessToken
        if (token.isNotEmpty()) {
            newBuilder.header(AUTHORIZATION, token.toBearerFormat)
        }

        return chain.proceed(newBuilder.build())
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}
