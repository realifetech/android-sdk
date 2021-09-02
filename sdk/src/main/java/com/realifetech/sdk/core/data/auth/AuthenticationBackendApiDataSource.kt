package com.realifetech.sdk.core.data.auth

import com.realifetech.sdk.core.data.token.AccessTokenBody
import com.realifetech.sdk.core.data.token.RefreshTokenBody
import com.realifetech.sdk.core.domain.ApiDataSource
import com.realifetech.sdk.core.domain.AuthenticationToken
import com.realifetech.sdk.core.network.AuthorizationApiNetwork
import java.util.*
import java.util.concurrent.TimeUnit

class AuthenticationBackendApiDataSource(private val authorizationApiNetwork: AuthorizationApiNetwork) :
    ApiDataSource {
    override fun getAccessToken(
        clientSecret: String,
        clientId: String
    ): AuthenticationToken.AccessTokenInfo? {
        val responseBody =
            authorizationApiNetwork.get()
                .getAuthToken(AccessTokenBody(clientSecret = clientSecret, clientId = clientId))
                .execute().body()
        return if (responseBody != null) {
            val timeNowMilliseconds = Calendar.getInstance().timeInMillis
            val expireTimeInMilliseconds =
                timeNowMilliseconds + TimeUnit.SECONDS.toMillis(responseBody.expiresIn.toLong())
            AuthenticationToken.AccessTokenInfo(responseBody.accessToken, expireTimeInMilliseconds)
        } else {
            null
        }
    }

    override fun refreshToken(
        clientSecret: String,
        clientId: String,
        refreshToken: String
    ): OAuthTokenResponse? {
        val tokenResponse = authorizationApiNetwork.get()
            .refreshAuthToken(
                RefreshTokenBody(
                    clientSecret = clientSecret,
                    clientId = clientId,
                    refreshToken = refreshToken,
                    grantType = GRANT_TYPE_REFRESH_TOKEN
                )
            ).execute().body()
        tokenResponse?.let {
            val timeNowMilliseconds = Calendar.getInstance().timeInMillis
            val expireTimeInMilliseconds =
                timeNowMilliseconds + TimeUnit.SECONDS.toMillis(it.expiresIn.toLong())

            return OAuthTokenResponse(
                it.accessToken,
                it.expiresIn,
                it.tokenType,
                it.scope,
                it.refreshToken,
                Date(expireTimeInMilliseconds)
            )
        }
        return null
    }

    companion object {
        private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    }
}