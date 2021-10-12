package com.realifetech.sdk.content.widgets.data.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetWidgetsByScreenIdQuery
import com.realifetech.GetWidgetsByScreenTypeQuery
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.content.widgets.mocks.WidgetMocks
import com.realifetech.sdk.content.widgets.mocks.WidgetMocks.PAGE
import com.realifetech.sdk.content.widgets.mocks.WidgetMocks.PAGE_SIZE
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class WidgetsDataSourceImplTest {

    @RelaxedMockK
    lateinit var apolloClient: ApolloClient
    private lateinit var widgetDataSource: WidgetsDataSourceImpl

    private lateinit var widgetByIdData: Response<GetWidgetsByScreenIdQuery.Data>
    private lateinit var widgetByIdSlot: CapturingSlot<ApolloCall.Callback<GetWidgetsByScreenIdQuery.Data>>
    private lateinit var widgetByTypeData: Response<GetWidgetsByScreenTypeQuery.Data>
    private lateinit var widgetByTypeSlot: CapturingSlot<ApolloCall.Callback<GetWidgetsByScreenTypeQuery.Data>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        widgetDataSource = WidgetsDataSourceImpl(apolloClient)
        initMockedFields()
    }

    private fun initMockedFields() {
        widgetByIdData = mockk()
        widgetByIdSlot = slot()
        widgetByTypeData = mockk()
        widgetByTypeSlot = slot()

    }

    @Test
    fun `get Screen By Screen Type returns data`() = runBlockingTest {
        // Given
        every { widgetByTypeData.data?.getWidgetsByScreenType?.fragments?.fragmentWidget } returns WidgetMocks.fragmentWidget
        //When
        getWidgetsByTypeCall()
        //Then
        widgetDataSource.getWidgetsByScreenType(
            WidgetMocks.screenType, PAGE_SIZE, PAGE
        ) { error, response ->
            assertEquals(null, error)
            assertEquals(WidgetMocks.widgetEdge, response)
        }

    }

    @Test
    fun `get Screen By Screen Type throws exception`() {
        // Given
        every { widgetByTypeData.data?.getWidgetsByScreenType?.fragments?.fragmentWidget } throws ApolloHttpException(
            ArgumentMatchers.any()
        )
        //When
        getWidgetsByTypeCall()
        //Then
        widgetDataSource.getWidgetsByScreenType(
            WidgetMocks.screenType, PAGE_SIZE, PAGE
        ) { error, response ->
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }

    }

    @Test
    fun `get Screen By Screen Type returns failure`() {
        // Given
        every { widgetByTypeData.data?.getWidgetsByScreenType?.fragments?.fragmentWidget } throws ApolloHttpException(
            ArgumentMatchers.any()
        )
        //When
        getWidgetsByTypeCall(true)
        //Then
        widgetDataSource.getWidgetsByScreenType(
            WidgetMocks.screenType, PAGE_SIZE, PAGE
        ) { error, response ->
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Screen By Screen Type returns null`() {
        // Given
        val callback = { error: Exception?, response: WidgetEdge? ->
            assertEquals(null, error)
            assertEquals(null, response)
        }
        getWidgetsByTypeCall()
        every { widgetByTypeData.data?.getWidgetsByScreenType?.fragments?.fragmentWidget } returns null
        //When
        widgetDataSource.getWidgetsByScreenType(
            WidgetMocks.screenType, PAGE_SIZE, PAGE
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }

        every { widgetByTypeData.data?.getWidgetsByScreenType?.fragments } returns null
        //When
        widgetDataSource.getWidgetsByScreenType(
            WidgetMocks.screenType, PAGE_SIZE, PAGE
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { widgetByTypeData.data?.getWidgetsByScreenType } returns null
        //When
        widgetDataSource.getWidgetsByScreenType(
            WidgetMocks.screenType, PAGE_SIZE, PAGE
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { widgetByTypeData.data } returns null
        //When
        widgetDataSource.getWidgetsByScreenType(
            WidgetMocks.screenType, PAGE_SIZE, PAGE
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }
    }

    private fun getWidgetsByTypeCall(shouldFail: Boolean = false) {
        every {
            apolloClient.query(GetWidgetsByScreenTypeQuery(WidgetMocks.screenType, PAGE_SIZE, PAGE))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK).build()
                .enqueue(capture(widgetByTypeSlot))
        } answers {
            if (shouldFail) {
                widgetByTypeSlot.captured.onFailure(ApolloException(""))
            } else {
                widgetByTypeSlot.captured.onResponse(widgetByTypeData)
            }
        }
    }

    @Test
    fun `get Screen By Screen Id returns data`() = runBlockingTest {
        // Given
        every { widgetByIdData.data?.getWidgetsByScreenId?.fragments?.fragmentWidget } returns WidgetMocks.fragmentWidget
        getWidgetsByIdCall()
        //When
        widgetDataSource.getWidgetsByScreenId(
            WidgetMocks.screenId, PAGE_SIZE,
            PAGE
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(WidgetMocks.widgetEdge, response)
        }

    }

    @Test
    fun `get Screen By Screen Id throws exception`() {
        // Given
        every { widgetByIdData.data?.getWidgetsByScreenId?.fragments?.fragmentWidget } throws ApolloHttpException(
            ArgumentMatchers.any()
        )
        //When
        getWidgetsByIdCall()
        //Then
        widgetDataSource.getWidgetsByScreenId(
            WidgetMocks.screenId, PAGE_SIZE,
            PAGE
        ) { error, response ->
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }

    }

    @Test
    fun `get Screen By Screen Id returns failure`() {
        // Given
        every { widgetByIdData.data?.getWidgetsByScreenId?.fragments?.fragmentWidget } throws ApolloHttpException(
            ArgumentMatchers.any()
        )
        //When
        getWidgetsByIdCall(true)
        //Then
        widgetDataSource.getWidgetsByScreenId(
            WidgetMocks.screenId, PAGE_SIZE,
            PAGE
        ) { error, response ->
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Screen By Screen Id returns null`() {
        // Given
        val callback = { error: Exception?, response: WidgetEdge? ->
            assertEquals(null, error)
            assertEquals(null, response)
        }
        getWidgetsByIdCall()
        every { widgetByIdData.data?.getWidgetsByScreenId?.fragments?.fragmentWidget } returns null
        //When
        widgetDataSource.getWidgetsByScreenId(
            WidgetMocks.screenId, PAGE_SIZE,
            PAGE
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }

        every { widgetByIdData.data?.getWidgetsByScreenId?.fragments } returns null
        //When
        widgetDataSource.getWidgetsByScreenId(
            WidgetMocks.screenId, PAGE_SIZE,
            PAGE
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { widgetByIdData.data?.getWidgetsByScreenId } returns null
        //When
        widgetDataSource.getWidgetsByScreenId(
            WidgetMocks.screenId, PAGE_SIZE,
            PAGE
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { widgetByIdData.data } returns null
        //When
        widgetDataSource.getWidgetsByScreenId(
            WidgetMocks.screenId, PAGE_SIZE,
            PAGE
        ) { error, response ->
            //Then
            callback.invoke(error, response)
        }
    }

    private fun getWidgetsByIdCall(shouldFail: Boolean = false) {
        every {
            apolloClient.query(GetWidgetsByScreenIdQuery(WidgetMocks.screenId, PAGE_SIZE, PAGE))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK).build()
                .enqueue(capture(widgetByIdSlot))
        } answers {
            if (shouldFail) {
                widgetByIdSlot.captured.onFailure(ApolloException(""))
            } else {
                widgetByIdSlot.captured.onResponse(widgetByIdData)
            }
        }
    }
}