package com.realifetech.sdk.communicate

import android.content.Context
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.communicate.data.Event
import com.realifetech.sdk.communicate.data.RegisterPushNotificationsResponse
import com.realifetech.sdk.communicate.domain.PushNotificationsTokenStorage
import com.realifetech.sdk.communicate.mocks.CommunicateMocks
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.ACTION
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.USER
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.errorBody
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.registerResponse
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.token
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.tokenBody
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.core.utils.hasNetworkConnection
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class CommunicateTest {

    @RelaxedMockK
    lateinit var tokenStorage: PushNotificationsTokenStorage

    @RelaxedMockK
    lateinit var realifetechApiV3Service: RealifetechApiV3Service

    @RelaxedMockK
    lateinit var analytics: Analytics

    @RelaxedMockK
    lateinit var context: Context

    private lateinit var communicate: Communicate
    private lateinit var mockedResult: Response<RegisterPushNotificationsResponse>
    private lateinit var callback: (error: Exception?, response: Boolean) -> Unit

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        communicate = Communicate(tokenStorage, realifetechApiV3Service, analytics, context)
        mockedResult = mockk()
        callback = mockk()
    }

    @Test
    fun `sending analytics track for PN when trackPush is called`() {
        // WHEN
        communicate.trackPush(
            Event.RECEIVED, mapOf(), callback
        )
        // THEN
        verify { analytics.track(USER, Event.RECEIVED.message, mapOf(), null, callback) }
    }

    @Test
    fun `register For Push Notifications results success`() {
        registerCall()
        every { mockedResult.body() } returns registerResponse
        val result = communicate.registerForPushNotifications(token)
        assert(result is Result.Success)
        assertEquals(true, (result as Result.Success).data)
        verify { tokenStorage.removePendingToken() }

    }

    @Test
    fun `register For Push Notifications results failure`() {
        registerCall(false)
        every { mockedResult.errorBody()?.string() } returns errorBody
        val result = communicate.registerForPushNotifications(token)
        assert(result is Result.Error)
        assert((result as Result.Error).exception is RuntimeException)
        assertEquals(errorBody, result.exception.message)

    }

    @Test
    fun `register For Push Notifications results null failure`() {
        registerCall(false)
        every { mockedResult.errorBody() } returns null
        val result = communicate.registerForPushNotifications(token)
        assert(result is Result.Error)
        assert((result as Result.Error).exception is RuntimeException)
        assertEquals(null, result.exception.message)
    }

    @Test
    fun `resend Pending Token `() {
        every { tokenStorage.hasPendingToken } returns true
        every { tokenStorage.pendingToken } returns token
        registerCall(true)
        communicate.resendPendingToken()
        verify { communicate.registerForPushNotifications(token) }
    }

    @Test
    fun `resend Pending Token has no stored token`() {
        every { tokenStorage.hasPendingToken } returns false
        communicate.resendPendingToken()
        justRun { communicate.resendPendingToken() }
    }

    private fun registerCall(isSuccessful: Boolean = true) {
        every { context.hasNetworkConnection } returns true
        every {
            realifetechApiV3Service.pushNotifications(
                CommunicateMocks.ID,
                tokenBody
            ).execute()
        } returns mockedResult
        every { mockedResult.isSuccessful } returns isSuccessful
    }

}