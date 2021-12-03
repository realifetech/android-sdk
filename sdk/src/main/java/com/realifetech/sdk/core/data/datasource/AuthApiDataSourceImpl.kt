package com.realifetech.sdk.core.data.datasource

import com.realifetech.sdk.core.data.model.auth.OAuthTokenResponse
import com.realifetech.sdk.core.data.model.token.AccessTokenBody
import com.realifetech.sdk.core.data.model.token.AccessTokenInfo
import com.realifetech.sdk.core.data.model.token.RefreshTokenBody
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.DeviceCalendar
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthApiDataSourceImpl @Inject constructor(
    private val authorizationApiNetwork: RealifetechApiV3Service,
    private val deviceCalendar: DeviceCalendar
) :
    AuthApiDataSource {
    override fun getAccessToken(
        clientSecret: String,
        clientId: String
    ): AccessTokenInfo? {
        val responseBody =
            authorizationApiNetwork
                .getAuthToken(AccessTokenBody(clientSecret = clientSecret, clientId = clientId))
                .execute().body()
        return if (responseBody != null) {
            val timeNowMilliseconds = deviceCalendar.currentTime
            val expireTimeInMilliseconds =
                timeNowMilliseconds + TimeUnit.SECONDS.toMillis(responseBody.expiresIn.toLong())
            AccessTokenInfo(responseBody.accessToken, expireTimeInMilliseconds)
        } else {
            null
        }
    }

    override fun refreshToken(
        clientSecret: String,
        clientId: String,
        refreshToken: String
    ): OAuthTokenResponse? {
        val tokenResponse = authorizationApiNetwork
            .refreshAuthToken(
                RefreshTokenBody(
                    clientSecret = clientSecret,
                    clientId = clientId,
                    refreshToken = refreshToken,
                    grantType = GRANT_TYPE_REFRESH_TOKEN
                )
            ).execute().body()
        tokenResponse?.let {
            val calendar = deviceCalendar
            calendar.add(Calendar.DAY_OF_YEAR, CAL_DAYS)
            calendar.add(Calendar.HOUR_OF_DAY, CAL_HOURS)

            return OAuthTokenResponse(
                it.accessToken,
                it.expiresIn,
                it.tokenType,
                EMPTY,
                it.refreshToken,
                calendar.time
            )
        }
        return null
    }

    companion object {
        private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
        private const val CAL_HOURS = -2
        private const val CAL_DAYS = 14
        private const val EMPTY = ""
    }
}