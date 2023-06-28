package com.realifetech.sdk.core.data.database.preferences.platform

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.realifetech.sdk.core.data.database.preferences.AbstractPreferences
import com.realifetech.sdk.core.data.model.auth.OAuthTokenResponse
import timber.log.Timber
import javax.inject.Inject


/** Platform Preferences  allow us to access our Frontier app Preferences
 * The reason why we need to do that is to sync the tokens when the user is logged in in Frontier
 * so that the sdk can continue perform the necessary calls using the right access token.
 **/
class PlatformPreferences @Inject constructor(context: Context) : AbstractPreferences(context) {

    override val preferencesStorageName = REALIFETECH_PREFERENCES
    private val TAG = PlatformPreferences::class.simpleName

    var rltToken: OAuthTokenResponse?
        get() {
            val jsonToken = preferences.getString(OAUTH_TOKEN, DEFAULT_EMPTY_STRING)
            try {
                return Gson().fromJson(jsonToken, OAuthTokenResponse::class.java)
            } catch (e: JsonSyntaxException) {
                Timber.tag(TAG).e(e)
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
        private const val OAUTH_TOKEN = "oauth-token"
        private const val REALIFETECH_PREFERENCES = "LiveStyledPreferences"
        private const val DEFAULT_EMPTY_STRING = ""
    }
}