package com.realifetech.sdk.core.data

import com.realifetech.sdk.core.domain.AuthenticationToken
import com.realifetech.sdk.core.network.AccessTokenBody
import com.realifetech.sdk.core.network.AuthorizationApiNetwork
import java.util.*
import java.util.concurrent.TimeUnit

internal class AuthenticationBackendApiDataSource : AuthenticationToken.ApiDataSource {
    override fun getAccessToken(clientSecret: String): AuthenticationToken.AccessTokenInfo? {
        val responseBody =
            AuthorizationApiNetwork.get().getAuthToken(AccessTokenBody(clientSecret = clientSecret)).execute().body()
        return if (responseBody != null) {
            val timeNowMilliseconds = Calendar.getInstance().timeInMillis
            val expireTimeInMilliseconds =
                timeNowMilliseconds + TimeUnit.SECONDS.toMillis(responseBody.expiresIn.toLong())
            AuthenticationToken.AccessTokenInfo(responseBody.accessToken, expireTimeInMilliseconds)
        } else {
            null
        }
    }
}