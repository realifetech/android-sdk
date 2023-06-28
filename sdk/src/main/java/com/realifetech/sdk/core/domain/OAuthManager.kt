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
    /** Get access token from the token storage*/
    val accessToken: String
        get() = authTokenStorage.accessToken

    /** This is a list that holds all the pending API calls that needs an access token*/
    private val pendingCalls = mutableListOf<() -> Unit>()

    /**
     * This function ensures that the access token is active.
     * If the token is not available or is expired, we will execute a call to refresh it.
     * Otherwise, we will do nothing and just process all pending calls.
     */
    fun ensureActive(call: (() -> Unit)? = null) {
        if (call != null) {
            pendingCalls.add(call)
        }

        platformTokenStorage.rltToken?.let { oAuthTokenResponse ->
            if (oAuthTokenResponse.accessToken.isNotBlank() && oAuthTokenResponse.refreshToken.isNotBlank()) {
                authTokenStorage.accessToken = oAuthTokenResponse.accessToken
            }
            if (authTokenStorage.isTokenExpired) {
                if (oAuthTokenResponse.refreshToken.isEmpty()) {
                    requestAccessTokenServer()
                } else {
                    requestRefreshTokenServer(oAuthTokenResponse.refreshToken)
                }
            }
        } ?: run {
            if (!authTokenStorage.isTokenExpired) {
                processPendingCalls()
            } else {
                requestAccessTokenServer()
            }
        }
    }

    /**
     * This function is called when a new access token is required.
     * It makes an API call to the server to get a new token and stores it.
     * After the new token is stored, it processes all pending API calls.
     */
    private fun requestAccessTokenServer() {
        val accessTokenInfo =
            authApiLazyWrapper.get().getAccessToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId
            ) ?: return

        val currentTimeMillis = System.currentTimeMillis()
        val expireAtMilliseconds =
            currentTimeMillis + (accessTokenInfo.expireAtMilliseconds * 1000L)

        authTokenStorage.accessToken = accessTokenInfo.token
        authTokenStorage.expireAtMilliseconds = expireAtMilliseconds

        processPendingCalls()
    }

    /**
     * This function is used to refresh the current access token.
     * It makes an API call to the server to refresh the token and stores it.
     * After the new token is stored, it processes all pending API calls.
     */
    private fun requestRefreshTokenServer(refreshToken: String) {
        val tokenResponse =
            authApiLazyWrapper.get().refreshToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId,
                refreshToken
            ) ?: return

        val currentTimeMillis = System.currentTimeMillis()
        val expireAtMilliseconds = currentTimeMillis + (tokenResponse.expiresIn * 1000L)

        platformTokenStorage.rltToken = tokenResponse
        authTokenStorage.expireAtMilliseconds = expireAtMilliseconds

        processPendingCalls()
    }

    /**
     * This function is used to process all pending API calls that require an active access token.
     * It executes all the calls in the 'pendingCalls' list and then clears the list.
     */
    private fun processPendingCalls() {
        pendingCalls.forEach { it() }
        pendingCalls.clear()
    }
}