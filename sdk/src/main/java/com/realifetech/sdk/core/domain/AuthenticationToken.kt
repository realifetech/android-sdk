package com.realifetech.sdk.core.domain

import com.realifetech.sdk.core.data.auth.AuthenticationTokenStorage
import com.realifetech.sdk.core.data.auth.OAuthTokenResponse
import com.realifetech.sdk.core.database.preferences.Preferences

class AuthenticationToken(
    private val storage: AuthenticationTokenStorage,
    private val apiSource: ApiDataSource,
    private val preferences: Preferences
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
                RLTConfiguration.CLIENT_SECRET,
                RLTConfiguration.APP_CODE + "_0"
            ) ?: return
        storage.accessToken = accessTokenInfo.token
        storage.expireAtMilliseconds = accessTokenInfo.expireAtMilliseconds
    }

    private fun requestRefreshTokenServer(refreshToken: String) {
        val tokenResponse =
            apiSource.refreshToken(
                RLTConfiguration.CLIENT_SECRET,
                RLTConfiguration.APP_CODE + "_0",
                refreshToken
            ) ?: return
        preferences.rltToken = tokenResponse
    }

    interface ApiDataSource {
        fun getAccessToken(clientSecret: String, clientId: String): AccessTokenInfo?
        fun refreshToken(
            clientSecret: String,
            clientId: String,
            refreshToken: String
        ): OAuthTokenResponse?
    }

    data class AccessTokenInfo(val token: String, val expireAtMilliseconds: Long)
}