package com.realifetech.sdk.core.data.database.preferences.configuration

import android.content.Context
import androidx.core.content.edit
import com.realifetech.sdk.core.data.constants.ConfigConstants
import com.realifetech.sdk.core.data.database.preferences.AbstractPreferences
import com.realifetech.sdk.core.data.model.config.CoreConfiguration

class ConfigurationStorage(context: Context) : AbstractPreferences(context) {

    var apiUrl: String
        get() {
            val value = preferences.getString(API_URL, ConfigConstants.apiUrl).orEmpty()
            return if (value.endsWith(BACK_SLASH)) {
                value
            } else {
                "$value/"
            }
        }
        set(value) {
            preferences.edit { putString(API_URL, value) }
        }
    var graphApiUrl: String
        get() = preferences.getString(GRAPHQL_URL, ConfigConstants.graphApiUrl).orEmpty()
        set(value) {
            preferences.edit { putString(GRAPHQL_URL, value) }
        }


    var webOrderingJourneyUrl: String
        get() = preferences.getString(ORDERING_JOURNEY_URL, ConfigConstants.webOrderingJourneyUrl)
            .orEmpty()
        set(value) {
            preferences.edit { putString(ORDERING_JOURNEY_URL, value) }
        }

    var clientSecret: String
        get() = preferences.getString(CLIENT_SECRET, ConfigConstants.clientSecret).orEmpty()
        set(value) {
            preferences.edit { putString(CLIENT_SECRET, value) }
        }
    var appCode: String
        get() = preferences.getString(APP_CODE, ConfigConstants.appCode).orEmpty()
        set(value) {
            preferences.edit { putString(APP_CODE, value) }
        }

    var userId: String?
        get() = preferences.getString(USER_ID, null)
        set(value) {
            preferences.edit { putString(USER_ID, value) }
        }

    fun set(value: CoreConfiguration) {
        value.let {
            appCode = it.appCode
            clientSecret = it.clientSecret
            apiUrl = it.apiUrl
            graphApiUrl = it.graphApiUrl
            webOrderingJourneyUrl = it.webOrderingJourneyUrl
        }
    }

    internal var deviceId: String
        get() = preferences.getString(DEVICE_ID, EMPTY) ?: EMPTY
        set(deviceId) {
            preferences.edit().putString(DEVICE_ID, deviceId).apply()
        }

    companion object {
        private const val APP_CODE = "app_code"
        private const val CLIENT_SECRET = "client_secret"
        private const val USER_ID = "user_id"
        private const val ORDERING_JOURNEY_URL = "ordering_journey_url"
        private const val API_URL = "api_url"
        private const val GRAPHQL_URL = "graphql_url"
        private const val DEVICE_ID = "device_id"
        private const val BACK_SLASH = "/"
    }
}