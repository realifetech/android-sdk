package com.realifetech.core_sdk.data

import com.realifetech.core_sdk.domain.AuthenticationToken
import com.realifetech.core_sdk.network.AccessTokenBody
import com.realifetech.core_sdk.network.AuthorizationApiNetwork
import java.util.*
import java.util.concurrent.TimeUnit

internal class AuthenticationBackendApiDataSource : AuthenticationToken.ApiDataSource {
    override fun getAccessToken(clientSecret: String, clientId: String): AuthenticationToken.AccessTokenInfo? {
        val responseBody =
            AuthorizationApiNetwork.get()
                .getAuthToken(AccessTokenBody(clientSecret = clientSecret, clientId = clientId)).execute().body()
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