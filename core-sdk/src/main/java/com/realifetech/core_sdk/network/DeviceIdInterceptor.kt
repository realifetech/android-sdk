package com.realifetech.core_sdk.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class DeviceIdInterceptor   (private val deviceId: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()

        newBuilder.addHeader("X-LS-DeviceId", deviceId)

        return chain.proceed(newBuilder.build())
    }
}
