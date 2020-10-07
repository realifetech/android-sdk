package com.realifetech.sdk.communicate.network

import com.realifetech.sdk.communicate.domain.RegisterPushNotificationsResponse
import com.realifetech.sdk.communicate.domain.TokenBody
import com.realifetech.sdk.general.domain.DeviceRegisterRequest
import com.realifetech.sdk.general.domain.DeviceRegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

internal interface CommunicationApiService {
    @POST("/v3/device/{id}/token")
    fun pushNotifications(
        @Path("id") id: String,
        @Body tokenBody: TokenBody
    ): Call<RegisterPushNotificationsResponse>
}