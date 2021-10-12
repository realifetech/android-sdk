package com.realifetech.sdk.core.data.database.preferences.auth

import android.content.Context
import androidx.core.content.edit
import com.realifetech.sdk.core.data.database.preferences.AbstractPreferences
import java.util.*

class AuthTokenStorage(context: Context) : AbstractPreferences(context) {

    var accessToken: String
        get() = preferences.getString(ACCESS_TOKEN_KEY, "").orEmpty()
        set(value) {
            preferences.edit { putString(ACCESS_TOKEN_KEY, value) }
        }

    var expireAtMilliseconds: Long
        get() = preferences.getLong(EXPIRE_TOKEN_TIME_KEY, 0)
        set(value) {
            preferences.edit { putLong(EXPIRE_TOKEN_TIME_KEY, value) }
        }

    val isTokenExpired: Boolean
        get() {
            val timeNow = Calendar.getInstance().timeInMillis
            return timeNow >= expireAtMilliseconds
        }

    companion object {
        private const val ACCESS_TOKEN_KEY = "AccessTokenOAuth"
        private const val EXPIRE_TOKEN_TIME_KEY = "ExpireTokenOAuth"
    }
}