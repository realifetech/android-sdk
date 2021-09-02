package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.database.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class DeviceIdInterceptor(private val preferences: Preferences) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()
        newBuilder.addHeader("X-LS-DeviceId", preferences.deviceId)
        return chain.proceed(newBuilder.build())
    }
}
