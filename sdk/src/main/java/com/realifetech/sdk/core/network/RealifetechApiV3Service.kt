package com.realifetech.sdk.core.network

import com.realifetech.sdk.communicate.data.TokenBody
import com.realifetech.sdk.communicate.data.RegisterPushNotificationsResponse
import com.realifetech.sdk.core.data.model.auth.OAuthTokenResponse
import com.realifetech.sdk.core.data.model.token.AccessTokenBody
import com.realifetech.sdk.core.data.model.token.RefreshTokenBody
import com.realifetech.sdk.core.data.model.device.DeviceRegisterRequest
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse
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