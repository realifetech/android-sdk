package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.mocks.NetworkEndpoints
import com.realifetech.sdk.core.mocks.NetworkMocks.deviceRegister
import com.realifetech.sdk.core.mocks.NetworkMocks.deviceResponse
import com.realifetech.sdk.core.mocks.NetworkMocks.refreshTokenBody
import com.realifetech.sdk.core.mocks.NetworkMocks.tokenResponse
import com.realifetech.sdk.general.mocks.GeneralMocks
import com.realifetech.sdk.util.Constants
import io.mockk.every
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class OAuth2AuthenticatorTest : BaseNetworkTest() {

    @Before
    fun setUp() {
        super.setup()
        val responses = listOf(
            MockResponse().setResponseCode(401),
            MockResponse()
                .setResponseCode(200)
                .setBody(tokenResponse),
            MockResponse()
                .setResponseCode(200)
                .setBody(deviceResponse)
        )
        provideResponses(responses)
    }

    @Test
    fun authenticate() {
        every { authTokenStorage.accessToken } returns Constants.EMPTY
        every { platformPreferences.rltToken } returns null
        every { oAuthManager.get().ensureActive() } answers {
            realifetechApiV3Service.refreshAuthToken(
                refreshTokenBody
            ).execute()
        }
        val response =
            realifetechApiV3Service.registerDevice(GeneralMocks.deviceRequest).execute()
        server.takeRequest()
        server.takeRequest()
        val request3 = server.takeRequest()
        assert(response.isSuccessful)
        assertEquals(NetworkEndpoints.REGISTER_DEVICE.value, request3.path)
        assertEquals(deviceRegister.message, response.body()?.message)


    }
}