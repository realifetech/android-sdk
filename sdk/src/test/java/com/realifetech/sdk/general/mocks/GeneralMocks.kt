package com.realifetech.sdk.general.mocks

import com.realifetech.sdk.core.data.model.device.DeviceRegisterRequest
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse
import com.realifetech.sdk.core.data.model.exceptions.NetworkException
import com.realifetech.sdk.general.data.PhysicalDeviceInfo
import okhttp3.ResponseBody

object GeneralMocks {
    val deviceRequest = DeviceRegisterRequest(
        "0ea67cc4-b6e9-42fb-a9d7-2578b8d2ade7:com.realifetech.sample",
        "ANDROID",
        "1.0",
        "29",
        "model",
        "manufacturer",
        123,
        100,
        false,
        wifiOn = true,
        wifiConnected = true
    )
    val errorBody = DeviceRegisterResponse(400, "NetworkException", "backend isn't ready, please try again later")
    val deviceRegisterResponse = DeviceRegisterResponse(200, "success", "all good")
    val networkError=  NetworkException(errorBody.code, errorBody.message)
}