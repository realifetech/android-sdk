package com.realifetech.sdk.communicate.data

import com.realifetech.sdk.communicate.mocks.CommunicateMocks.nullRegisterResponse
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.registerResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class RegisterPushNotificationsResponseTest {


    @Test
    fun isSuccess() {
        assertEquals(registerResponse?.isSuccess, true)
        assertEquals(nullRegisterResponse.isSuccess, false)
    }

    @Test
    fun getPlayerId() {
        assertEquals(registerResponse?.playerId, "Player ID")
        assertEquals(nullRegisterResponse.playerId, null)
    }
}