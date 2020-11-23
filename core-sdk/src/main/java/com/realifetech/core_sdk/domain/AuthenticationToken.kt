package com.realifetech.core_sdk.domain

import com.realifetech.core_sdk.data.AuthenticationTokenStorage

class AuthenticationToken(
    private val storage: AuthenticationTokenStorage,
    private val apiSource: ApiDataSource
) {

    val accessToken: String
        get() = storage.accessToken

    /**
     * This will ensure that we have an active token. If the token is not available or is expired, we will execute a
     * call to refresh it. Otherwise we will do nothing.
     */
    fun ensureActive() {
        if (!storage.isTokenExpired) return

        requestAccessTokenServer()
    }

    private fun requestAccessTokenServer() {
        val accessTokenInfo =
            apiSource.getAccessToken(CoreConfiguration.clientSecret, CoreConfiguration.appCode + "_0") ?: return
        storage.accessToken = accessTokenInfo.token
        storage.expireAtMilliseconds = accessTokenInfo.expireAtMilliseconds
    }

    interface ApiDataSource {
        fun getAccessToken(clientSecret: String, clientId: String): AccessTokenInfo?
    }

    data class AccessTokenInfo(val token: String, val expireAtMilliseconds: Long)
}