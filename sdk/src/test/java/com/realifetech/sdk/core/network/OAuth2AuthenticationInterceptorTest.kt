package com.realifetech.sdk.core.network

import com.realifetech.sdk.communicate.mocks.CommunicateMocks
import com.realifetech.sdk.core.data.model.shared.`object`.toBearerFormat
import com.realifetech.sdk.core.mocks.NetworkEndpoints.PUSH_NOTIFICATION
import com.realifetech.sdk.core.mocks.NetworkMocks
import com.realifetech.sdk.core.mocks.NetworkMocks.AUTHORIZATION
import com.realifetech.sdk.core.mocks.NetworkMocks.accessToken
import com.realifetech.sdk.core.mocks.NetworkMocks.rltToken
import com.realifetech.sdk.util.Constants.EMPTY
import io.mockk.every
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class OAuth2AuthenticationInterceptorTest : BaseNetworkTest() {

    @Before
    fun setUp() {
        super.setup()
        provideResponses(
            listOf(
                MockResponse().setResponseCode(200).setBody(NetworkMocks.notification)
            )
        )
    }

    @Test
    fun `intercept with auth token when access token is present`() {
        every { authTokenStorage.accessToken } returns accessToken
        every { platformPreferences.rltToken } returns null
        val response =
            realifetechApiV3Service.pushNotifications("1", CommunicateMocks.tokenBody).execute()
        assertEquals("sup,bra?", response.body()?.playerId)
        val request1 = server.takeRequest()
        assertEquals(PUSH_NOTIFICATION.value, request1.path)
        assertNotNull(request1.getHeader(AUTHORIZATION))
        assertEquals(request1.getHeader(AUTHORIZATION), accessToken.toBearerFormat)
        server.shutdown()
    }


    @Test
    fun `intercept with auth token when access token is not present`() {
        every { authTokenStorage.accessToken } returns EMPTY
        every { platformPreferences.rltToken } returns null
        val response =
            realifetechApiV3Service.pushNotifications("1", CommunicateMocks.tokenBody).execute()
        assertEquals("sup,bra?", response.body()?.playerId)
        val request1 = server.takeRequest()
        assertEquals(PUSH_NOTIFICATION.value, request1.path)
        assertNull(request1.getHeader(AUTHORIZATION))
        server.shutdown()
    }

    @Test
    fun `intercept with auth token when rlt token is present`() {
        every { authTokenStorage.accessToken } returns EMPTY
        every { platformPreferences.rltToken } returns rltToken
        val response =
            realifetechApiV3Service.pushNotifications("1", CommunicateMocks.tokenBody).execute()
        assertEquals(true, response.body()?.isSuccess)
        val request1 = server.takeRequest()
        assertEquals(PUSH_NOTIFICATION.value, request1.path)
        assertNotNull(request1.getHeader(AUTHORIZATION))
        assertEquals(request1.getHeader(AUTHORIZATION), rltToken.accessToken.toBearerFormat)
    }

    @Test
    fun `intercept with auth token when rlt token is present but access token is null`() {
        every { authTokenStorage.accessToken } returns EMPTY
        every { platformPreferences.rltToken?.accessToken } returns EMPTY
        val response =
            realifetechApiV3Service.pushNotifications("1", CommunicateMocks.tokenBody).execute()
        assertEquals(true, response.body()?.isSuccess)
        val request1 = server.takeRequest()
        assertEquals(PUSH_NOTIFICATION.value, request1.path)
        assertNull(request1.getHeader(AUTHORIZATION))
    }


    @After
    fun tearDown() {
        server.shutdown()
    }

}