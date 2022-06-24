package com.realifetech.sdk.communicate.data

import com.realifetech.sdk.communicate.mocks.CommunicateMocks
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.GOOGLE
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.token
import org.junit.Assert.assertEquals
import org.junit.Test

class TokenBodyTest {

    @Test
    fun isSuccess() {
        assertEquals(CommunicateMocks.tokenBody.providerToken, token)
    }

    @Test
    fun getPlayerId() {
        assertEquals(CommunicateMocks.tokenBody.provider, GOOGLE)
    }
}