package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
import com.realifetech.sdk.core.data.model.shared.`object`.toBearerFormat
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class OAuth2AuthenticationInterceptor(
    private val authTokenStorage: AuthTokenStorage,
    private val platformPreferences: PlatformPreferences
) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()
        val accessToken =
            if (platformPreferences.rltToken != null) {
                platformPreferences.rltToken?.accessToken
            } else {
                authTokenStorage.accessToken
            }
        accessToken?.apply {
            if (isNotEmpty()) {
                newBuilder.header(AUTHORIZATION, this.toBearerFormat)
            }
        }

        return chain.proceed(newBuilder.build())
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}
