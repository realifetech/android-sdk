package com.realifetech.sdk.core.domain

import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
import com.realifetech.sdk.core.data.datasource.AuthApiDataSource
import com.realifetech.sdk.di.Injector
import javax.inject.Inject

class OAuthManager {

    @Inject
    lateinit var authTokenStorage: AuthTokenStorage

    @Inject
    lateinit var authApiSource: AuthApiDataSource

    @Inject
    lateinit var platformTokenStorage: PlatformPreferences

    @Inject
    lateinit var configurationStorage: ConfigurationStorage

    init {
    Injector.getComponent().inject(this)
    }

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
            authApiSource.getAccessToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode + SUFFIX
            ) ?: return
        authTokenStorage.accessToken = accessTokenInfo.token
        authTokenStorage.expireAtMilliseconds = accessTokenInfo.expireAtMilliseconds
    }

    private fun requestRefreshTokenServer(refreshToken: String) {
        val tokenResponse =
            authApiSource.refreshToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode + SUFFIX,
                refreshToken
            ) ?: return
        platformTokenStorage.rltToken = tokenResponse
    }

    companion object {
        private const val SUFFIX = "_0"
    }
}