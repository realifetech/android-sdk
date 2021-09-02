package com.realifetech.sdk.core.domain

import com.realifetech.sdk.core.data.config.CoreConfiguration


object RLTConfiguration {
    private var defaultValues = CoreConfiguration("", "")
    var APP_CODE: String = defaultValues.appCode
    var CLIENT_SECRET: String = defaultValues.clientSecret
    var API_URL: String = defaultValues.apiUrl
    var GRAPHQL_URL: String = defaultValues.graphApiUrl
    var DEVICE_ID: String = ""
    var ORDERING_JOURNEY_URL: String = defaultValues.webOrderingJourneyUrl
    val finalApiUrl: String
        get() {
            return if (API_URL.endsWith("/")) {
                API_URL
            } else {
                "$API_URL/"
            }
        }

    internal fun set(updatedConfiguration: CoreConfiguration) {
        defaultValues = updatedConfiguration
    }
}