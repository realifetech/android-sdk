package com.realifetech.sdk.core.data.datasource

import com.realifetech.sdk.core.data.model.auth.OAuthTokenResponse
import com.realifetech.sdk.core.data.model.token.AccessTokenInfo

interface AuthApiDataSource {
    fun getAccessToken(clientSecret: String, clientId: String): AccessTokenInfo?
    fun refreshToken(
        clientSecret: String,
        clientId: String,
        refreshToken: String
    ): OAuthTokenResponse?
}