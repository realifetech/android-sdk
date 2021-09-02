package com.realifetech.sdk.core.network

import com.realifetech.sdk.communicate.data.TokenBody
import com.realifetech.sdk.communicate.data.RegisterPushNotificationsResponse
import com.realifetech.sdk.core.data.auth.OAuthTokenResponse
import com.realifetech.sdk.core.data.token.AccessTokenBody
import com.realifetech.sdk.core.data.token.RefreshTokenBody
import com.realifetech.sdk.general.data.DeviceRegisterRequest
import com.realifetech.sdk.general.data.DeviceRegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface RealifetechApiV3Service {
    @POST("/v3/oauth/v2/token")
    fun getAuthToken(@Body request: AccessTokenBody): Call<OAuthTokenResponse>

    @POST("/v3/oauth/v2/token")
    fun refreshAuthToken(@Body refreshTokenBody: RefreshTokenBody): Call<OAuthTokenResponse>

    // General
    @POST("/v3/device/register")
    fun registerDevice(@Body deviceRegisterRequest: DeviceRegisterRequest): Call<DeviceRegisterResponse>

    //Communicate
    @POST("/v3/device/{id}/token")
    fun pushNotifications(
        @Path("id") id: String,
        @Body tokenBody: TokenBody
    ): Call<RegisterPushNotificationsResponse>
}