package com.realifetech.sdk.core.network

import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.domain.AuthenticationToken
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class DeviceIdInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()

        newBuilder.addHeader("X-LS-DeviceId", RealifeTech.getGeneral().deviceIdentifier)

        return chain.proceed(newBuilder.build())
    }
}
