package com.realifetech.sdk.core.database.preferences

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.realifetech.sdk.core.data.auth.OAuthTokenResponse
import javax.inject.Inject

class Preferences @Inject constructor(context: Context) : AbstractPreferences(context) {

    var deviceId: String
        get() = preferences.getString(DEVICE_ID, DEFAULT_EMPTY_STRING) ?: DEFAULT_EMPTY_STRING
        set(deviceId) {
            preferences.edit().putString(DEVICE_ID, deviceId).apply()
        }
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

    companion object {
        private const val DEFAULT_EMPTY_STRING = ""
        private const val OAUTH_TOKEN = "oauth-token"
        private const val DEVICE_ID = "device_id"
    }
}