package com.realifetech.sdk.communicate.data

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.realifetech.sdk.general.General

class PushNotificationsTokenPreferenceStorage : PushNotificationsTokenStorage.DataSource {

    private val preferenceManager: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(General.instance.configuration.requireContext())

    override var token: String
        get() = preferenceManager.getString(TOKEN_KEY, "").orEmpty()
        set(value) {
            preferenceManager.edit { putString(TOKEN_KEY, value) }
        }

    override fun removeToken() {
        preferenceManager.edit { remove(TOKEN_KEY) }
    }

    companion object {
        private const val TOKEN_KEY = "PushNotificationsToken"
    }
}