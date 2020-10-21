package com.realifetech.sdk.core.network.graphQl

import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.domain.AuthenticationToken
import okhttp3.Interceptor
import okhttp3.Response

internal class GraphQlHeadersInterceptor(private val accessToken: AuthenticationToken) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        accessToken.ensureActive()
        val token = accessToken.accessToken

        val request = chain.request().newBuilder()
            .addHeader("X-LS-DeviceId", RealifeTech.getGeneral().deviceIdentifier)
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}