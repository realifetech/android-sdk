package com.realifetech.sdk.analytics.domain

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.PutAnalyticEventMutation
import com.realifetech.sdk.analytics.mocks.AnalyticsMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class RltBackendAnalyticsEngineTest {

    @RelaxedMockK
    lateinit var apolloClient: ApolloClient
    private lateinit var rltBackendAnalyticsEngine: RltBackendAnalyticsEngine
    private lateinit var response: Response<PutAnalyticEventMutation.Data>
    private lateinit var captureSlot: CapturingSlot<ApolloCall.Callback<PutAnalyticEventMutation.Data>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        rltBackendAnalyticsEngine = RltBackendAnalyticsEngine(apolloClient)
        response = mockk()
        captureSlot = CapturingSlot()
    }

    @Test
    fun `log Event results with success`() = runBlocking {
        every {
            response.data
        } returns AnalyticsMocks.successData
        putEventSuccessfully()
        rltBackendAnalyticsEngine.logEvent(AnalyticsMocks.analyticEventWrapper) { error, response ->
            Assert.assertEquals(null, error)
            Assert.assertEquals(true, response)
        }

    }

    @Test
    fun `log Event results with exception`() = runBlocking {
        every {
            response.data
        } throws ApolloHttpException(any())
        putEventSuccessfully()
        rltBackendAnalyticsEngine.logEvent(AnalyticsMocks.analyticEventWrapper) { error, response ->
            assert(error is ApolloHttpException)
            Assert.assertEquals(false, response)
        }

    }

    @Test
    fun `log Event results with failure`() = runBlocking {
        every {
            response.data
        } returns AnalyticsMocks.successData
        every {
            apolloClient.mutate(PutAnalyticEventMutation(AnalyticsMocks.analyticEvent))
                .enqueue(capture(captureSlot))
        } answers {
            captureSlot.captured.onFailure(ApolloException("Error"))
        }
        rltBackendAnalyticsEngine.logEvent(AnalyticsMocks.analyticEventWrapper) { error, response ->
            assert(error is ApolloException)
            Assert.assertEquals(false, response)
        }

    }

    @Test
    fun `log Event results with null data`() = runBlocking {
        val callback = { error: Exception?, response: Boolean ->
            assert(error is Exception)
            Assert.assertEquals(false, response)
        }
        every {
            response.data
        } returns null
        putEventSuccessfully()
        rltBackendAnalyticsEngine.logEvent(AnalyticsMocks.analyticEventWrapper) { error, response ->
            callback(error, response)
        }
        every {
            response.data?.putAnalyticEvent
        } returns null

        rltBackendAnalyticsEngine.logEvent(AnalyticsMocks.analyticEventWrapper) { error, response ->
            callback(error, response)
        }
    }


    private fun putEventSuccessfully() {
        every {
            apolloClient.mutate(PutAnalyticEventMutation(AnalyticsMocks.analyticEvent))
                .enqueue(capture(captureSlot))
        } answers {
            captureSlot.captured.onResponse(response)
        }
    }
}