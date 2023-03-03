package com.realifetech.sample.data

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.realifetech.sample.BuildConfig

class DeviceConfigurationStorage(context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var apiUrl: String
        get() = preferences.getString(API_URL, EMPTY).orEmpty()
        set(value) {
            preferences.edit { putString(API_URL, value) }
        }

    var graphQl: String
        get() = preferences.getString(GRAPHQL_URL, EMPTY).orEmpty()
        set(value) {
            preferences.edit { putString(GRAPHQL_URL, value) }
        }

    var orderingJourney: String
        get() = preferences.getString(ORDERING_JOURNEY_URL, EMPTY).orEmpty()
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

    var pushNotificationsToken: String
        get() = preferences.getString(PUSH_TOKEN, EMPTY).orEmpty()
        set(value) {
            preferences.edit { putString(PUSH_TOKEN, value) }
        }

    var salt: String
        get() = preferences.getString(SALT, "a0238b93efda10f5df56").orEmpty()
        set(value) {
            preferences.edit { putString(SALT, value) }
        }

    var isLoggedIn: Boolean
        get() = preferences.getBoolean(LOGGED_IN, false)
        set(value) {
            preferences.edit { putBoolean(LOGGED_IN, value) }
        }

    var appVersion: String
        get() = preferences.getString(APP_VERSION, BuildConfig.VERSION_NAME).orEmpty()
        set(value) {
            preferences.edit { putString(APP_VERSION, value) }
        }

    companion object {
        private const val EMPTY = ""
        private const val PUSH_TOKEN = "PushToken"
        private const val SALT = "SALT"
        private const val LOGGED_IN = "LoggedIn"
        private const val APP_CODE = "app_code"
        private const val CLIENT_SECRET = "client_secret"
        private const val ORDERING_JOURNEY_URL = "ordering_journey_url"
        private const val API_URL = "api_url"
        private const val GRAPHQL_URL = "graphql_url"
        private const val APP_VERSION = "appVersion"
    }
}