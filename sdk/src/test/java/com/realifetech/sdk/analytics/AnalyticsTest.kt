package com.realifetech.sdk.analytics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper
import com.realifetech.sdk.analytics.domain.AnalyticsEngine
import com.realifetech.sdk.analytics.domain.AnalyticsStorage
import com.realifetech.sdk.analytics.mocks.AnalyticsMocks
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.utils.DeviceCalendar
import com.realifetech.sdk.general.General
import com.realifetech.sdk.sell.utils.MainCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class AnalyticsTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var analyticsStorage: AnalyticsStorage

    @RelaxedMockK
    lateinit var analyticsEngine: AnalyticsEngine

    @RelaxedMockK
    lateinit var deviceCalendar: DeviceCalendar

    @RelaxedMockK
    lateinit var configurationStorage: ConfigurationStorage

    @RelaxedMockK
    lateinit var general: General
    lateinit var analytics: Analytics
    private lateinit var eventResponse: (error: Exception?, response: Boolean) -> Unit
    private lateinit var captureSlot: CapturingSlot<(error: Exception?, response: Boolean) -> Unit>
    private lateinit var pendingCaptureSlot: CapturingSlot<(response: Boolean) -> Unit>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        analytics =
            Analytics(
                analyticsEngine,
                analyticsStorage,
                general,
                testDispatcher, testDispatcher,
                deviceCalendar,
                configurationStorage
            )
        eventResponse = mockk()
        captureSlot = CapturingSlot()
        pendingCaptureSlot = CapturingSlot()
        every { deviceCalendar.currentTime } returns CURRENT_TIME
        every { configurationStorage.userId } returns null

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `log Event while sdk is ready returns error and send pending events`() = runBlockingTest {
        val event = AnalyticsMocks.analyticEventWrapper
        initTrackEventMocks(event, Exception(ERROR_MESSAGE), false)
        initLogPendingEvent(true)
        analytics.track(
            TYPE,
            ACTION,
            null,
            null
        ) { error, _ ->
            assert(error is Exception)
            Assert.assertEquals(ERROR_MESSAGE, error?.message)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `log Event while sdk is ready returns error and empty pending events`() = runBlockingTest {
        val event = AnalyticsMocks.analyticEventWrapper
        initTrackEventMocks(event, Exception(ERROR_MESSAGE), false)
        initLogPendingEvent(false)
        analytics.track(
            TYPE,
            ACTION,
            null,
            null
        ) { error, _  ->
            assert(error is Exception)
            Assert.assertEquals(ERROR_MESSAGE, error?.message)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `log Event while sdk is ready result with success`() = runBlockingTest {
        val event = AnalyticsMocks.analyticEventWrapper
        initTrackEventMocks(event, null, true)
        analytics.track(
            TYPE,
            ACTION,
            null,
            null
        ) { error, _  ->
            Assert.assertEquals(null, error)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `log Event while sdk is ready results with null completion`() = runBlockingTest {
        val event = AnalyticsMocks.analyticEventWrapper
        initTrackEventMocks(event, null, true)
        analytics.track(
            TYPE,
            ACTION,
            null,
            null,
            null
        )
    }


    @Test
    fun `log Event while sdk is not ready result with RunTimeException`() = runBlockingTest {
        val event = AnalyticsMocks.analyticEventWrapper
        initTrackEventMocks(event, null, response = false, sdkReady = false)
        analytics.track(
            TYPE,
            ACTION,
            null,
            null
        ) { error, _  ->
            assert(error is RuntimeException)
            Assert.assertEquals(RUNTIME_EXCEPTION_MESSAGE, error?.message)
        }

        verify {
            analyticsStorage.save(event)
        }
    }

    private fun initTrackEventMocks(
        event: AnalyticEventWrapper,
        error: Exception?,
        response: Boolean,
        sdkReady: Boolean = true
    ) {
        every { general.isSdkReady } returns sdkReady
        coEvery { analyticsEngine.track(event, capture(captureSlot)) }
            .coAnswers { captureSlot.captured.invoke(error, response) }
    }

    private fun initLogPendingEvent(isEmpty: Boolean) {
        coEvery { analyticsEngine.sendPendingEvents(capture(pendingCaptureSlot)) }
            .coAnswers { pendingCaptureSlot.captured.invoke(isEmpty) }
    }

    companion object {
        private const val TYPE = "OrderButton"
        private const val ERROR_MESSAGE = "batata"
        private const val ACTION = "click"
        private const val RUNTIME_EXCEPTION_MESSAGE = "The SDK is not ready yet"
        private const val CURRENT_TIME = 1234L

    }

}