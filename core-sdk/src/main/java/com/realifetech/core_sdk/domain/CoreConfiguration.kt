package com.realifetech.core_sdk.domain

import android.content.Context

object CoreConfiguration {
    lateinit var context: Context
    var appCode: String = ""
    var clientSecret: String = ""
    var apiUrl = "https://api.livestyled.com/v3"
    var graphApiUrl = "https://graphql-eu.realifetech.com/"
    var deviceId: String = ""

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