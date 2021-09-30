package com.realifetech.sdk.communicate.mocks

import com.realifetech.sdk.communicate.data.RegisterPushNotificationsResponse
import com.realifetech.sdk.communicate.data.TokenBody

object CommunicateMocks {


    val errorBody = "backend is on holiday, no data available"
    val registerResponse: RegisterPushNotificationsResponse? =
        RegisterPushNotificationsResponse(true, "Player ID")
    val nullRegisterResponse: RegisterPushNotificationsResponse =
        RegisterPushNotificationsResponse()
    const val ID = "me"
    const val GOOGLE = "GOOGLE"
    val token = "this is a token, don't but you can't see it"
    val tokenBody= TokenBody(GOOGLE, token)
}