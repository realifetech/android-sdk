package com.realifetech.sdk.general.domain

import android.content.Context
import com.realifetech.sdk.communicate.Communicate
import kotlin.concurrent.thread

class DeviceConfiguration {
    var context: Context? = null
        set(value) {
            field = value
            if (value != null) {
                thread {
                    Communicate.instance.resendPendingToken()
                }
            }
        }
    var appCode: String = ""
    var clientSecret: String = ""
    var apiUrl = "https://api.livestyled.com/v3"
    var graphApiUrl = "https://graphql-eu.realifetech.com/"

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