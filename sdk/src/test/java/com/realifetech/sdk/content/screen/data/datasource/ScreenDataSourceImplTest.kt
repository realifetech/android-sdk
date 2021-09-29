package com.realifetech.sdk.content.screen.data.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetScreenByIdQuery
import com.realifetech.GetScreenByScreenTypeQuery
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.resultTranslationById
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.resultTranslationByType
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.screenId
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.screenType
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.translationsById
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.translationsByType
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

@ExperimentalCoroutinesApi
class ScreenDataSourceImplTest {

    @RelaxedMockK
    lateinit var apolloClient: ApolloClient
    private lateinit var screenDataSource: ScreenDataSourceImpl

    private lateinit var screenByIdData: Response<GetScreenByIdQuery.Data>
    private lateinit var screenByIdSlot: CapturingSlot<ApolloCall.Callback<GetScreenByIdQuery.Data>>
    private lateinit var screenByTypeData: Response<GetScreenByScreenTypeQuery.Data>
    private lateinit var screenByTypeSlot: CapturingSlot<ApolloCall.Callback<GetScreenByScreenTypeQuery.Data>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        screenDataSource = ScreenDataSourceImpl(apolloClient)
        initMockedFields()
    }

    private fun initMockedFields() {
        screenByIdData = mockk()
        screenByIdSlot = slot()
        screenByTypeData = mockk()
        screenByTypeSlot = slot()

    }

    @Test
    fun `get Screen By Screen Type returns data`() = runBlockingTest {
        // Given
        every { screenByTypeData.data?.getScreenByScreenType?.translations } returns translationsByType
        //When
        getScreenByTypeCall()
        //Then
        screenDataSource.getScreenByScreenType(screenType) { error, response ->
            assertEquals(null, error)
            assertEquals(resultTranslationByType, response)
        }

    }

    @Test
    fun `get Screen By Screen Type throws exception`() {
        // Given
        every { screenByTypeData.data?.getScreenByScreenType?.translations } throws ApolloHttpException(
            any()
        )
        //When
        getScreenByTypeCall()
        //Then
        screenDataSource.getScreenByScreenType(screenType) { error, response ->
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }

    }

    @Test
    fun `get Screen By Screen Type returns failure`() {
        // Given
        every { screenByTypeData.data?.getScreenByScreenType?.translations } throws ApolloHttpException(
            any()
        )
        //When
        getScreenByTypeCall(true)
        //Then
        screenDataSource.getScreenByScreenType(screenType) { error, response ->
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Screen By Screen Type returns null`() {
        // Given
        val callback = { error: Exception?, response: List<Translation>? ->
            assertEquals(null, error)
            assertEquals(null, response)
        }
        getScreenByTypeCall()
        every { screenByTypeData.data?.getScreenByScreenType?.translations } returns null
        //When
        screenDataSource.getScreenByScreenType(screenType) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { screenByTypeData.data?.getScreenByScreenType } returns null
        //When
        screenDataSource.getScreenByScreenType(screenType) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { screenByTypeData.data } returns null
        //When
        screenDataSource.getScreenByScreenType(screenType) { error, response ->
            //Then
            callback.invoke(error, response)
        }
    }

    private fun getScreenByTypeCall(shouldFail: Boolean = false) {
        coEvery {
            apolloClient.query(GetScreenByScreenTypeQuery(screenType)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK).build()
                .enqueue(capture(screenByTypeSlot))
        } answers {
            if (shouldFail) {
                screenByTypeSlot.captured.onFailure(ApolloException(""))
            } else {
                screenByTypeSlot.captured.onResponse(screenByTypeData)
            }
        }
    }

    @Test
    fun `get Screen By Screen Id returns data`() = runBlockingTest {
        // Given
        every { screenByIdData.data?.getScreenById?.translations } returns translationsById
        getScreenByIdCall()
        //When
        screenDataSource.getScreenById(screenId) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(resultTranslationById, response)
        }

    }

    @Test
    fun `get Screen By Screen Id throws exception`() {
        // Given
        every { screenByIdData.data?.getScreenById?.translations } throws ApolloHttpException(any())
        //When
        getScreenByIdCall()
        //Then
        screenDataSource.getScreenById(screenId) { error, response ->
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }

    }

    @Test
    fun `get Screen By Screen Id returns failure`() {
        // Given
        every { screenByIdData.data?.getScreenById?.translations } throws ApolloHttpException(any())
        //When
        getScreenByIdCall(true)
        //Then
        screenDataSource.getScreenById(screenId) { error, response ->
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Screen By Screen Id returns null`() {
        // Given
        val callback = { error: Exception?, response: List<Translation>? ->
            assertEquals(null, error)
            assertEquals(null, response)
        }
        getScreenByIdCall()
        every { screenByIdData.data?.getScreenById?.translations } returns null
        //When
        screenDataSource.getScreenById(screenId) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { screenByIdData.data?.getScreenById } returns null
        //When
        screenDataSource.getScreenById(screenId) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { screenByIdData.data } returns null
        //When
        screenDataSource.getScreenById(screenId) { error, response ->
            //Then
            callback.invoke(error, response)
        }
    }

    private fun getScreenByIdCall(shouldFail: Boolean = false) {
        coEvery {
            apolloClient.query(GetScreenByIdQuery(screenId)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK).build()
                .enqueue(capture(screenByIdSlot))
        } answers {
            if (shouldFail) {
                screenByIdSlot.captured.onFailure(ApolloException(""))
            } else {
                screenByIdSlot.captured.onResponse(screenByIdData)
            }
        }
    }

}