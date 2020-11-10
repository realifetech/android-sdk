package com.realifetech.sample.data

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

class DeviceConfigurationStorage(private val context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var apiUrl: String
        get() = preferences.getString("API_URL", "").orEmpty()
        set(value) {
            preferences.edit { putString("API_URL", value) }
        }

    var graphQl: String
        get() = preferences.getString("GRAPH_URL", "").orEmpty()
        set(value) {
            preferences.edit { putString("GRAPH_URL", value) }
        }

    var clientSecret: String
        get() = preferences.getString("ClientSecret", "").orEmpty()
        set(value) {
            preferences.edit { putString("ClientSecret", value) }
        }

    var clientId: String
        get() = preferences.getString("ClientId", "").orEmpty()
        set(value) {
            preferences.edit { putString("ClientId", value) }
        }

    var pushNotificationsToken: String
        get() = preferences.getString("PushToken", "").orEmpty()
        set(value) {
            preferences.edit { putString("PushToken", value) }
        }
}