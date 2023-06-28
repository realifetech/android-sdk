package com.realifetech.sdk.di.core

import com.realifetech.realifetech_sdk.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

abstract class BaseRestClientModule {
    companion object {
        @JvmStatic
        fun createHttpLoggingInterceptor(logTag: String): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor { message -> Timber.tag(logTag).d(message) }

            if (BuildConfig.BUILD_TYPE == "debug" ) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.NONE
            }
            return logging
        }

    }
}