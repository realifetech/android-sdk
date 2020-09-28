package com.realifetech.sdk.general.network

import com.realifetech.sdk.general.domain.DeviceRegisterRequest
import com.realifetech.sdk.general.domain.DeviceRegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DeviceApiService {
    @POST("/v3/device/register")
    fun registerDevice(@Body deviceRegisterRequest: DeviceRegisterRequest): Call<DeviceRegisterResponse>
}