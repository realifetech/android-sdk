package com.realifetech.core_sdk.database.preferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.realifetech.core_sdk.data.auth.OAuthTokenResponse
import com.realifetech.core_sdk.domain.CoreConfiguration.context

object Preferences {
    var rltToken: OAuthTokenResponse?
        get() {
            val jsonToken = preferences.getString(OAUTH_TOKEN, DEFAULT_EMPTY_STRING)
            try {
                return Gson().fromJson(jsonToken, OAuthTokenResponse::class.java)
            } catch (e: JsonSyntaxException) {
                Log.e(this.javaClass.name, "Couldn't getOAuthToken: %s", e)
            }
            return null
        }
        set(rltToken) {
            rltToken?.apply {
                if (tokenType.trim().isNotEmpty() && accessToken.trim().isNotEmpty()) {
                    val gson = Gson()
                    val jsonToken = gson.toJson(this)
                    preferences.edit().putString(OAUTH_TOKEN, jsonToken).apply()
                }
            }
        }
    private val preferences: SharedPreferences =
        context.getSharedPreferences(getPreferencesName(), Context.MODE_PRIVATE)


    private fun getPreferencesName(): String {
        return CONCERT_LIVE_PREFERENCES + context.packageName
    }

    private const val CONCERT_LIVE_PREFERENCES = "LiveStyledPreferences"
    private const val DEFAULT_EMPTY_STRING = ""
    private const val OAUTH_TOKEN = "oauth-token"
}