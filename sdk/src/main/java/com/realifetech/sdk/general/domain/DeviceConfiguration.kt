package com.realifetech.sdk.general.domain

import android.content.Context
import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.sdk.communicate.Communicate
import kotlin.concurrent.thread

class DeviceConfiguration {
    var context: Context? = null
        set(value) {
            field = value
            if (value != null) {
                CoreConfiguration.context = value
                thread {
                    Communicate.instance.resendPendingToken()
                }
            }
        }
    var appCode: String = ""
        set(value) {
            field = value
            CoreConfiguration.appCode = value
        }
    var clientSecret: String = ""
        set(value) {
            field = value
            CoreConfiguration.clientSecret = value
        }
    var apiUrl = "https://api.livestyled.com/v3"
        set(value) {
            field = value
            CoreConfiguration.apiUrl = value
        }
    var graphApiUrl = "https://graphql-eu.realifetech.com/"
        set(value) {
            field = value
            CoreConfiguration.graphApiUrl = value
        }

    fun requireContext(): Context {
        return this.context
            ?: throw RuntimeException("The context was not set. Use the Configuration module to set the Context")
    }

    /**
     * Use this property as [apiUrl], as this will ensure you have a valid base url when using Retrofit library
     */
    internal val finalApiUrl: String
        get() {
            return if (apiUrl.endsWith("/")) {
                apiUrl
            } else {
                "$apiUrl/"
            }
        }
}