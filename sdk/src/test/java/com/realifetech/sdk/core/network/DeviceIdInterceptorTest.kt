package com.realifetech.sdk.core.network

import com.realifetech.sdk.communicate.mocks.CommunicateMocks
import com.realifetech.sdk.core.mocks.NetworkEndpoints
import com.realifetech.sdk.core.mocks.NetworkMocks
import com.realifetech.sdk.core.mocks.NetworkMocks.DEVICE_ID_HEADER
import com.realifetech.sdk.core.mocks.NetworkMocks.deviceId
import io.mockk.every
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeviceIdInterceptorTest : BaseNetworkTest() {


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
    fun `test if device id has been setup correctly in the call header`() {
        every { configurationStorage.deviceId } returns deviceId
        val response = realifetechApiV3Service.pushNotifications("1", CommunicateMocks.tokenBody).execute()
        Assert.assertEquals(true, response.body()?.isSuccess)
        val request1 = server.takeRequest()
        Assert.assertEquals(NetworkEndpoints.PUSH_NOTIFICATION.value, request1.path)
        Assert.assertNotNull(request1.getHeader(DEVICE_ID_HEADER))
        Assert.assertEquals(deviceId,request1.getHeader(DEVICE_ID_HEADER))
    }
}