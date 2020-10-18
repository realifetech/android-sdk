package com.realifetech.sdk.core.data

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.realifetech.sdk.general.General

class AuthenticationTokenPreferenceStorage : AuthenticationTokenStorage.DataSource {

    private val preferenceManager: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(General.instance.configuration.context!!)

    override var accessToken: String
        get() = preferenceManager.getString(ACCESS_TOKEN_KEY, "").orEmpty()
        set(value) {
            preferenceManager.edit { putString(ACCESS_TOKEN_KEY, value) }
        }

    override var expireTimeSince1970Milliseconds: Long
        get() = preferenceManager.getLong(EXPIRE_TOKEN_TIME_KEY, 0)
        set(value) {
            preferenceManager.edit { putLong(EXPIRE_TOKEN_TIME_KEY, value) }
        }

    companion object {
        private const val ACCESS_TOKEN_KEY = "AccessTokenOAuth"
        private const val EXPIRE_TOKEN_TIME_KEY = "ExpireTokenOAuth"
    }
}