package com.realifetech.sdk.core.mocks

import com.google.gson.Gson
import com.realifetech.sdk.communicate.data.RegisterPushNotificationsResponse
import com.realifetech.sdk.core.data.model.auth.OAuthTokenResponse
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse
import com.realifetech.sdk.core.data.model.token.RefreshTokenBody
import java.util.*

object NetworkMocks {
    // Note: the test might fail if you run it after 04/29/2100

    const val AUTHORIZATION = "Authorization"
    const val DEVICE_ID_HEADER = "X-LS-DeviceId"
    const val accessToken = "access token"
    const val expireAtMilliSeconds = 4112636400000L
    val rltToken = OAuthTokenResponse(
        accessToken,
        10,
        "Bearer",
        "scope",
        "refresh token",
        Date(expireAtMilliSeconds)
    )
    val refreshTokenBody = RefreshTokenBody("Bearer", "refresh token", "LS", "it's a secret")
    val deviceRegister = DeviceRegisterResponse(200, "Success", "Noice")
    val deviceResponse = convertToJson(deviceRegister)
    val tokenResponse = convertToJson(rltToken)
    const val deviceId = "0ea67cc4-b6e9-42fb-a9d7-2578b8d2ade7:com.realifetech.sample"
    val notification = convertToJson(RegisterPushNotificationsResponse(true, "sup,bra?"))

    private fun <T> convertToJson(model: T): String {
        val gson = Gson()
        return gson.toJson(model)
    }
}
