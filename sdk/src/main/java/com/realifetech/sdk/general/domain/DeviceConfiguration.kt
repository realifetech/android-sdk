package com.realifetech.sdk.general.domain

import android.content.Context

class DeviceConfiguration {
    var context: Context? = null
    var appCode: String = ""
    var clientSecret: String = ""
    var apiUrl = "https://api.livestyled.com/v3"
    var graphApiUrl = "https://graphql-eu.realifetech.com/"

    fun requireContext(): Context {
        return this.context
            ?: throw RuntimeException("The context was not set. Use the Configuration module to set the Context")
    }
}