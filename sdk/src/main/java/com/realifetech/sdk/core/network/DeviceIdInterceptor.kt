package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class DeviceIdInterceptor @Inject constructor(
    private val storage: ConfigurationStorage
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()
        newBuilder.addHeader(DEVICE_ID_HEADER, storage.deviceId)
        return chain.proceed(newBuilder.build())
    }

    companion object {
        private const val DEVICE_ID_HEADER = "X-LS-DeviceId"
    }
}
