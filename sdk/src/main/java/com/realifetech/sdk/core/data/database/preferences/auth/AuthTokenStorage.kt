package com.realifetech.sdk.core.data.database.preferences.auth

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.realifetech.fragment.AuthToken
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

    var webAuthToken: AuthToken?
        get() {
            val jsonToken = preferences.getString(
                WEB_AUTH_TOKEN,
                EMPTY
            )
            try {
                return Gson().fromJson(jsonToken, AuthToken::class.java)
            } catch (e: JsonSyntaxException) {
                Log.e(this.javaClass.name, "Couldn't get AuthToken: %s", e)
            }
            return null
        }
        set(webAuthToke) {
            webAuthToke?.let {
                val gson = Gson()
                val jsonToken = gson.toJson(it)
                preferences.edit().putString(WEB_AUTH_TOKEN, jsonToken).apply()
            }
        }

    fun deleteWebAuthToken() {
        preferences.edit().remove(WEB_AUTH_TOKEN).apply()
    }
    companion object {
        private const val WEB_AUTH_TOKEN = "WebAuthToken"
        private const val ACCESS_TOKEN_KEY = "AccessTokenOAuth"
        private const val EXPIRE_TOKEN_TIME_KEY = "ExpireTokenOAuth"
    }
}