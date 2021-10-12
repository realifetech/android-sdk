package com.realifetech.sdk.core.domain

import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
import com.realifetech.sdk.core.data.datasource.AuthApiDataSource
import com.realifetech.sdk.core.data.model.shared.`object`.toClientId
import dagger.Lazy

class OAuthManager(
    private val authTokenStorage: AuthTokenStorage,
    private val authApiLazyWrapper: Lazy<AuthApiDataSource>,
    private val platformTokenStorage: PlatformPreferences,
    private val configurationStorage: ConfigurationStorage
) {

    val accessToken: String
        get() {
            var token = platformTokenStorage.rltToken?.accessToken
            if (token.isNullOrBlank())
                token = authTokenStorage.accessToken
            return token
        }

    /**
     * This will ensure that we have an active token. If the token is not available or is expired, we will execute a
     * call to refresh it. Otherwise we will do nothing.
     */
    fun ensureActive() {
        platformTokenStorage.rltToken?.let {
            if (!it.isTokenExpired) return
            requestRefreshTokenServer(it.refreshToken)
        } ?: run {
            if (!authTokenStorage.isTokenExpired) return
            requestAccessTokenServer()
        }
    }

    private fun requestAccessTokenServer() {
        val accessTokenInfo =
            authApiLazyWrapper.get().getAccessToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId
            ) ?: return
        authTokenStorage.accessToken = accessTokenInfo.token
        authTokenStorage.expireAtMilliseconds = accessTokenInfo.expireAtMilliseconds
    }

    private fun requestRefreshTokenServer(refreshToken: String) {
        val tokenResponse =
            authApiLazyWrapper.get().refreshToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId,
                refreshToken
            ) ?: return
        platformTokenStorage.rltToken = tokenResponse
    }
}