package com.realifetech.sdk.core.domain

import com.realifetech.sdk.core.data.auth.AuthenticationTokenStorage
import com.realifetech.sdk.core.data.auth.OAuthTokenResponse
import com.realifetech.sdk.core.database.configuration.ConfigurationStorage
import com.realifetech.sdk.core.database.preferences.Preferences
import javax.inject.Inject

class AuthenticationToken @Inject constructor(
    private val storage: AuthenticationTokenStorage,
    private val apiSource: ApiDataSource,
    private val preferences: Preferences,
    private val configurationStorage: ConfigurationStorage
) {
    val accessToken: String
        get() {
            var token = preferences.rltToken?.accessToken
            if (token.isNullOrBlank())
                token = storage.accessToken
            return token
        }
    /**
     * This will ensure that we have an active token. If the token is not available or is expired, we will execute a
     * call to refresh it. Otherwise we will do nothing.
     */
    fun ensureActive() {
        preferences.rltToken?.let {
            if (!it.isTokenExpired) return
            requestRefreshTokenServer(it.refreshToken)
        } ?: run {
            if (!storage.isTokenExpired) return
            requestAccessTokenServer()
        }
    }

    private fun requestAccessTokenServer() {
        val accessTokenInfo =
            apiSource.getAccessToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode + SUFFIX
            ) ?: return
        storage.accessToken = accessTokenInfo.token
        storage.expireAtMilliseconds = accessTokenInfo.expireAtMilliseconds
    }

    private fun requestRefreshTokenServer(refreshToken: String) {
        val tokenResponse =
            apiSource.refreshToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode + SUFFIX,
                refreshToken
            ) ?: return
        preferences.rltToken = tokenResponse
    }


    data class AccessTokenInfo(val token: String, val expireAtMilliseconds: Long)
    companion object {
        private const val SUFFIX = "_0"
    }
}