package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.mocks.NetworkEndpoints
import com.realifetech.sdk.core.mocks.NetworkMocks
import com.realifetech.sdk.general.mocks.GeneralMocks
import com.realifetech.sdk.util.Constants
import io.mockk.every
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UnauthenticatedCaseParserInterceptorTest : BaseNetworkTest() {

    @Before
    fun setUp() {
        super.setup()
        val responses = listOf(
            MockResponse()
                .setResponseCode(400)
                .setBody(NetworkMocks.unauthenticatedBody),
            MockResponse()
                .setResponseCode(200)
                .setBody(NetworkMocks.tokenResponse),
            MockResponse()
                .setResponseCode(200)
                .setBody(NetworkMocks.deviceResponse)
        )
        provideResponses(responses)
    }

    @Test
    fun `refresh token when getting custom unauthenticated error code`() {
        every { authTokenStorage.accessToken } returns Constants.EMPTY
        every { platformPreferences.rltToken } returns null
        every { oAuthManager.get().ensureActive() } answers {
            realifetechApiV3Service.refreshAuthToken(
                NetworkMocks.refreshTokenBody
            ).execute()
        }
        val response =
            realifetechApiV3Service.registerDevice(GeneralMocks.deviceRequest).execute()
        server.takeRequest()
        server.takeRequest()
        val request3 = server.takeRequest()
        assert(response.isSuccessful)
        assertEquals(NetworkEndpoints.REGISTER_DEVICE.value, request3.path)
        assertEquals(NetworkMocks.deviceRegister.message, response.body()?.message)

    }
}