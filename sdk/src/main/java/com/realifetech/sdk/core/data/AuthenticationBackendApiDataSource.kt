package com.realifetech.sdk.core.data

import com.realifetech.sdk.core.domain.AuthentificationToken
import com.realifetech.sdk.core.network.AccessTokenBody
import com.realifetech.sdk.core.network.AuthorizationApiNetwork
import java.util.*
import java.util.concurrent.TimeUnit

internal class AuthenticationBackendApiDataSource : AuthentificationToken.ApiDataSource {
    override fun getAccessToken(clientSecret: String): AuthentificationToken.AccessTokenInfo? {
        val responseBody =
            AuthorizationApiNetwork.get().getAuthToken(AccessTokenBody(clientSecret = clientSecret)).execute().body()
        return if (responseBody != null) {
            val timeNowMilliseconds = Calendar.getInstance().timeInMillis
            val expireTimeInMilliseconds =
                timeNowMilliseconds + TimeUnit.MINUTES.toMillis(responseBody.expiresIn.toLong())
            AuthentificationToken.AccessTokenInfo(responseBody.accessToken, expireTimeInMilliseconds)
        } else {
            null
        }
    }
}