package com.realifetech.sdk.communicate

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.communicate.data.Event
import com.realifetech.sdk.communicate.data.RegisterPushNotificationsResponse
import com.realifetech.sdk.communicate.data.model.NotificationConsent
import com.realifetech.sdk.communicate.data.model.NotificationConsentStatus
import com.realifetech.sdk.communicate.data.model.emptyNotificationConsent
import com.realifetech.sdk.communicate.domain.PushConsentRepository
import com.realifetech.sdk.communicate.domain.PushNotificationsTokenStorage
import com.realifetech.sdk.communicate.mocks.CommunicateMocks
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.USER
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.errorBody
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.registerResponse
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.token
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.tokenBody
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.core.utils.hasNetworkConnection
import com.realifetech.sdk.sell.utils.MainCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CommunicateTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var tokenStorage: PushNotificationsTokenStorage

    @RelaxedMockK
    lateinit var realifetechApiV3Service: RealifetechApiV3Service

    @RelaxedMockK
    lateinit var analytics: Analytics

    @RelaxedMockK
    lateinit var context: Context

    @RelaxedMockK
    lateinit var pushConsentRepository: PushConsentRepository

    private lateinit var communicate: Communicate
    private lateinit var mockedResult: Response<RegisterPushNotificationsResponse>
    private lateinit var callback: (error: Exception?, response: Boolean) -> Unit

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        communicate = Communicate(
            tokenStorage,
            realifetechApiV3Service,
            testDispatcher,
            testDispatcher,
            analytics,
            context,
            pushConsentRepository
        )
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
    fun `resend Pending Token `() = runBlocking {
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

    @Test
    fun `when call getNotificationConsents() and notification consent list is not empty, then return Success`() = runBlocking {
        val quoteList = listOf(dummyNotificationConsent())

        coEvery { pushConsentRepository.getNotificationConsents() } returns Result.Success(quoteList)

        val response = communicate.getNotificationConsents()

        assertEquals(response, Result.Success(quoteList))
    }

    @Test
    fun `when call getMyNotificationConsents() and notification consent list is null, then return Error`() = runBlocking {
        val error: Exception = Exception("Error message")

        coEvery { pushConsentRepository.getMyNotificationConsents() } returns Result.Error(error)

        val response = communicate.getMyNotificationConsents()

        assertEquals(response, Result.Error(error))
    }

    @Test
    fun `when call updateMyNotificationConsent() and response value is not null, then return Success(value)`() = runBlocking {
        val value: Boolean? = true

        coEvery { pushConsentRepository.updateMyNotificationConsent(CommunicateMocks.ID, true) } returns Result.Success(value)

        val response = communicate.updateMyNotificationConsent(CommunicateMocks.ID, true)

        assertEquals(response, Result.Success(value))
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

    private fun dummyNotificationConsent() = NotificationConsent(
        id = "",
        name = "",
        sortId = 0,
        status = NotificationConsentStatus.ACTIVE,
        translations = emptyList()
    )

}