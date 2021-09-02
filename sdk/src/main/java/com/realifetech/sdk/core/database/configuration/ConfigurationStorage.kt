package com.realifetech.sdk.core.database.configuration

import android.content.Context
import androidx.core.content.edit
import com.realifetech.sdk.core.data.config.CoreConfiguration
import com.realifetech.sdk.core.database.preferences.AbstractPreferences

class ConfigurationStorage(context: Context) : AbstractPreferences(context) {

    var apiUrl: String
        get() = preferences.getString(API_URL, "https://api.livestyled.com/v3/").orEmpty()
        set(value) {
            val finalUrl = if (value.endsWith("/")) {
                value
            } else {
                "$value/"
            }
            preferences.edit { putString(API_URL, finalUrl) }
        }
    var graphQl: String
        get() = preferences.getString(GRAPHQL_URL, "https://graphql-eu.realifetech.com/").orEmpty()
        set(value) {
            preferences.edit { putString(GRAPHQL_URL, value) }
        }

    var webOrderingJourneyUrl: String
        get() = preferences.getString(ORDERING_JOURNEY_URL, "https://ordering.realifetech.com/")
            .orEmpty()
        set(value) {
            preferences.edit { putString(ORDERING_JOURNEY_URL, value) }
        }

    var clientSecret: String
        get() = preferences.getString(CLIENT_SECRET, EMPTY).orEmpty()
        set(value) {
            preferences.edit { putString(CLIENT_SECRET, value) }
        }
    var appCode: String
        get() = preferences.getString(APP_CODE, EMPTY).orEmpty()
        set(value) {
            preferences.edit { putString(APP_CODE, value) }
        }

    fun set(value: CoreConfiguration) {
        value.let {
            appCode = it.appCode
            clientSecret = it.clientSecret
            apiUrl = it.apiUrl
            graphQl = it.graphApiUrl
            webOrderingJourneyUrl = it.webOrderingJourneyUrl
        }
    }


    companion object {
        private const val APP_CODE = "app_code"
        private const val CLIENT_SECRET = "client_secret"
        private const val ORDERING_JOURNEY_URL = "ordering_journey_url"
        private const val API_URL = "api_url"
        private const val GRAPHQL_URL = "graphql_url"
    }
}