package com.realifetech.sdk.core.mocks

import com.realifetech.sdk.core.data.model.auth.OAuthTokenResponse
import com.realifetech.sdk.core.data.model.token.AccessTokenBody
import com.realifetech.sdk.core.data.model.token.AccessTokenInfo
import com.realifetech.sdk.core.mocks.NetworkMocks.accessToken
import java.util.*

object AuthMocks {
    val tokenBody = AccessTokenBody(clientSecret = "It's a secret", clientId = "LS")
    val expiredRltToken = OAuthTokenResponse(
        accessToken,
        10,
        "Bearer",
        "scope",
        "refresh token",
        Date(1633863600000L)
    )
    const val refreshToken = "refresh token"
    private const val resultExpiryTime = 4112636410000L
    val rltTokenResult =
        OAuthTokenResponse(
            accessToken,
            10,
            "Bearer",
            "scope",
            "refresh token",
            Date(resultExpiryTime)
        )
    val accessTokenInfo = AccessTokenInfo(accessToken, resultExpiryTime)
}