package com.realifetech.sdk.core.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*
import javax.inject.Inject

class LanguageInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newBuilder = originalRequest.newBuilder()
        newBuilder.addHeader(LANGUAGE_HEADER, Locale.getDefault().language)
        return chain.proceed(newBuilder.build())
    }

    companion object {
        private const val LANGUAGE_HEADER = "Accept-Language"
    }
}
