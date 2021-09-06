package com.realifetech.sdk.core.data.database.preferences.platform

import android.content.Context
import com.google.gson.Gson
import com.realifetech.sdk.core.data.database.preferences.AbstractPreferences
import com.realifetech.sdk.core.data.model.auth.OAuthTokenResponse
import javax.inject.Inject


/** Platform Preferences  allow us to access our Frontier app Preferences
 * The reason why we need to do that is to sync the tokens when the user is logged in in Frontier
 * so that the sdk can continue perform the necessary calls using the right access token.
 **/
class PlatformPreferences @Inject constructor(context: Context) : AbstractPreferences(context) {

    override val preferencesStorageName = REALIFETECH_PREFERENCES

    var rltToken: OAuthTokenResponse?
        get() {
            val jsonToken = preferences.getString(OAUTH_TOKEN, EMPTY)
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
    }
}