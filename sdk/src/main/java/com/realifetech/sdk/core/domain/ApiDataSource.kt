package com.realifetech.sdk.core.domain

import com.realifetech.sdk.core.data.auth.OAuthTokenResponse

interface ApiDataSource {
    fun getAccessToken(clientSecret: String, clientId: String): AuthenticationToken.AccessTokenInfo?
    fun refreshToken(
        clientSecret: String,
        clientId: String,
        refreshToken: String
    ): OAuthTokenResponse?
}