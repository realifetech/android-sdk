package com.realifetech.sdk.sell.fulfilmentpoint.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetFulfilmentPointByIdQuery
import com.realifetech.GetFulfilmentPointCategoriesQuery
import com.realifetech.GetFulfilmentPointCategoryByIdQuery
import com.realifetech.GetFulfilmentPointsQuery
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.core.data.model.shared.`object`.asInput
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.NEXT_PAGE
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.PAGE
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.PAGE_SIZE
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.ffpCategories
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.ffpCategoryEdges
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.ffpEdges
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.filters
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.fragmentFulfilmentPoint
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.fragmentFulfilmentPointCategory
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.fulfilmentPointCategory
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.fulfilmentPointID
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.fulfilmentPoints
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.nullableFFPCategories
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.nullableFFPCategoryEdges
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.nullableFFPEdges
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.params
import com.realifetech.type.FulfilmentPointFilter
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class FulfilmentPointDataSourceImplTest {

    @RelaxedMockK
    private lateinit var apolloClient: ApolloClient
    private lateinit var fulfilmentPointDataSource: FulfilmentPointDataSourceImpl
    private lateinit var ffPointsData: Response<GetFulfilmentPointsQuery.Data>
    private lateinit var ffPointsSlot: CapturingSlot<ApolloCall.Callback<GetFulfilmentPointsQuery.Data>>
    private lateinit var ffPointData: Response<GetFulfilmentPointByIdQuery.Data>
    private lateinit var ffPointSlot: CapturingSlot<ApolloCall.Callback<GetFulfilmentPointByIdQuery.Data>>
    private lateinit var ffpCategoriesData: Response<GetFulfilmentPointCategoriesQuery.Data>
    private lateinit var ffpCategoriesSlot: CapturingSlot<ApolloCall.Callback<GetFulfilmentPointCategoriesQuery.Data>>
    private lateinit var ffpCategoryData: Response<GetFulfilmentPointCategoryByIdQuery.Data>
    private lateinit var ffpCategorySlot: CapturingSlot<ApolloCall.Callback<GetFulfilmentPointCategoryByIdQuery.Data>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        initMockedFields()
    }

    private fun initMockedFields() {
        fulfilmentPointDataSource = FulfilmentPointDataSourceImpl(apolloClient)
        // init getOrders mocked response data & capture
        ffPointsData = mockk()
        ffPointsSlot = slot()
        ffPointData = mockk()
        ffPointSlot = slot()
        ffpCategoriesData = mockk()
        ffpCategoriesSlot = slot()
        ffpCategoryData = mockk()
        ffpCategorySlot = slot()

    }

    @Test
    fun `get fulfilment Points with null filters and params results success`() = runBlocking {
        //Given
        every {
            ffPointsData.data?.getFulfilmentPoints?.edges
        } returns ffpEdges
        getFFPsSuccessAnswer(filters, params)
        // When
        fulfilmentPointDataSource.getFulfilmentPoints(
            PAGE_SIZE,
            PAGE, filters, params
        ) { error, response ->
            // Then
            val expected = PaginatedObject(
                fulfilmentPoints,
                NEXT_PAGE
            )
            assertEquals(
                expected, response
            )
            assertEquals(null, error)
        }

    }

    @Test
    fun `get fulfilment Points with filters and params results success`() = runBlocking {
        //Given
        every {
            ffPointsData.data?.getFulfilmentPoints?.edges
        } returns ffpEdges
        getFFPsSuccessAnswer()
        // When
        fulfilmentPointDataSource.getFulfilmentPoints(
            PAGE_SIZE,
            PAGE, null, null
        ) { error, response ->
            // Then
            val expected = PaginatedObject(
                fulfilmentPoints,
                NEXT_PAGE
            )
            assertEquals(
                expected, response
            )
            assertEquals(null, error)
        }

    }

    @Test
    fun `get  fulfilment Points results with throwable`() = runBlocking {
        //Given
        getFFPsSuccessAnswer()
        every {
            ffPointsData.data?.getFulfilmentPoints
        } throws ApolloHttpException(any())

        // When
        fulfilmentPointDataSource.getFulfilmentPoints(
            PAGE_SIZE,
            PAGE, null, null
        ) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get  fulfilment Points results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.query(
                GetFulfilmentPointsQuery(
                    PAGE_SIZE,
                    PAGE.toInput(),
                    FulfilmentPointFilter().toInput(),
                    Input.optional(null)
                )
            ).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK).build()
                .enqueue(capture(ffPointsSlot))
        } answers { ffPointsSlot.captured.onFailure(ApolloException("Error")) }

        // When
        fulfilmentPointDataSource.getFulfilmentPoints(
            PAGE_SIZE,
            PAGE, null, null
        ) { error, response ->
            // Then
            print(response)
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }


    @Test
    fun `get  fulfilment Points results with null response`() = runBlocking {
        // Given
        val errorCallback = { error: Exception?, response: PaginatedObject<FulfilmentPoint?>? ->
            // Then
            assert(error is Exception)
            Assert.assertSame(null, response)
        }
        getFFPsSuccessAnswer()
        // When
        every {
            ffPointsData.data?.getFulfilmentPoints?.edges
        } returns null
        fulfilmentPointDataSource.getFulfilmentPoints(
            PAGE_SIZE,
            PAGE,
            null,
            null
        ) { error, response ->
            // Then
            val expected: PaginatedObject<Order?> =
                PaginatedObject(null, NEXT_PAGE)
            assertEquals(null, error)
            assertEquals(expected, response)
        }

        every {
            ffPointsData.data?.getFulfilmentPoints?.edges
        } returns nullableFFPEdges

        fulfilmentPointDataSource.getFulfilmentPoints(
            PAGE_SIZE,
            PAGE,
            null,
            null
        ) { error, response ->
            // Then
            val expected = PaginatedObject(
                FFPMocks.nullableFFPoints, NEXT_PAGE
            )
            assertEquals(null, error)
            assertEquals(expected, response)
        }


        every {
            ffPointsData.data?.getFulfilmentPoints
        } returns null

        fulfilmentPointDataSource.getFulfilmentPoints(
            PAGE_SIZE,
            PAGE,
            null,
            null
        ) { error, response ->
            // Then
            errorCallback(error, response)
        }

        every {
            ffPointsData.data
        } returns null

        fulfilmentPointDataSource.getFulfilmentPoints(
            PAGE_SIZE,
            PAGE,
            null,
            null
        ) { error, response ->
            // Then
            errorCallback(error, response)
        }
    }


    private fun getFFPsSuccessAnswer(
        filters: Input<FulfilmentPointFilter>? = null,
        params: List<FilterParamWrapper>? = null
    ) {
        every {
            ffPointsData.data?.getFulfilmentPoints?.nextPage
        } returns NEXT_PAGE
        every {
            ffPointsData.errors
        } returns null
        every {
            apolloClient.query(
                GetFulfilmentPointsQuery(
                    PAGE_SIZE,
                    PAGE.toInput(),
                    filters = filters ?: FulfilmentPointFilter().toInput(),
                    params = Input.optional(params?.map { it.asInput })
                )
            ).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK).build()
                .enqueue(capture(ffPointsSlot))
        } answers { ffPointsSlot.captured.onResponse(ffPointsData) }
    }


    @Test
    fun `get Fulfilment Point By Id returns success`() {
        every {
            ffPointData.data?.getFulfilmentPoint?.fragments?.fragmentFulfilmentPoint
        } returns fragmentFulfilmentPoint
        getFFPSuccessAnswer()
        fulfilmentPointDataSource.getFulfilmentPointById(
            fulfilmentPointID,
            null
        ) { error, fulfilmentPoint ->
            assertEquals(null, error)
            assertEquals(FFPMocks.fulfilmentPoint, fulfilmentPoint)
        }
    }

    @Test
    fun `get Fulfilment Point By Id with params returns success`() {
        every {
            ffPointData.data?.getFulfilmentPoint?.fragments?.fragmentFulfilmentPoint
        } returns fragmentFulfilmentPoint
        getFFPSuccessAnswer(params)
        fulfilmentPointDataSource.getFulfilmentPointById(
            fulfilmentPointID,
            params
        ) { error, fulfilmentPoint ->
            assertEquals(null, error)
            assertEquals(FFPMocks.fulfilmentPoint, fulfilmentPoint)
        }
    }

    @Test
    fun `get Fulfilment Point By Id throws exception`() {
        every {
            ffPointData.data?.getFulfilmentPoint?.fragments?.fragmentFulfilmentPoint
        } throws ApolloHttpException(any())
        getFFPSuccessAnswer()
        fulfilmentPointDataSource.getFulfilmentPointById(
            fulfilmentPointID,
            null
        ) { error, fulfilmentPoint ->
            assert(error is ApolloHttpException)
            assertEquals(null, fulfilmentPoint)
        }
    }

    @Test
    fun `get Fulfilment Point By Id returns failure`() {
        every {
            apolloClient.query(
                GetFulfilmentPointByIdQuery(
                    fulfilmentPointID,
                    Input.optional(null)
                )
            ).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build().enqueue(capture(ffPointSlot))
        } answers { ffPointSlot.captured.onFailure(ApolloException("")) }

        fulfilmentPointDataSource.getFulfilmentPointById(
            fulfilmentPointID,
            null
        ) { error, fulfilmentPoint ->
            assert(error is ApolloException)
            assertEquals(null, fulfilmentPoint)
        }
    }

    @Test
    fun `get Fulfilment Point By Id returns null`() {
        // Given
        val callback = { error: Exception?, response: FulfilmentPoint? ->
            // Then
            Assert.assertSame(null, error)
            Assert.assertSame(null, response)
        }
        getFFPSuccessAnswer()
        every {
            ffPointData.data?.getFulfilmentPoint?.fragments?.fragmentFulfilmentPoint
        } returns null

        fulfilmentPointDataSource.getFulfilmentPointById(
            fulfilmentPointID,
            null
        ) { error, fulfilmentPoint ->
            callback.invoke(error, fulfilmentPoint)
        }
        every {
            ffPointData.data?.getFulfilmentPoint?.fragments
        } returns null

        fulfilmentPointDataSource.getFulfilmentPointById(
            fulfilmentPointID,
            null
        ) { error, fulfilmentPoint ->
            callback.invoke(error, fulfilmentPoint)
        }

        every {
            ffPointData.data?.getFulfilmentPoint
        } returns null

        fulfilmentPointDataSource.getFulfilmentPointById(
            fulfilmentPointID,
            null
        ) { error, fulfilmentPoint ->
            callback.invoke(error, fulfilmentPoint)
        }

        every {
            ffPointData.data
        } returns null

        fulfilmentPointDataSource.getFulfilmentPointById(
            fulfilmentPointID,
            null
        ) { error, fulfilmentPoint ->
            callback.invoke(error, fulfilmentPoint)
        }
    }

    private fun getFFPSuccessAnswer(
        params: List<FilterParamWrapper>? = null
    ) {
        every {
            apolloClient.query(
                GetFulfilmentPointByIdQuery(
                    fulfilmentPointID,
                    Input.optional(params?.map { it.asInput })
                )
            ).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build().enqueue(capture(ffPointSlot))
        } answers { ffPointSlot.captured.onResponse(ffPointData) }
    }

    @Test
    fun `get Fulfilment Point Categories returns success`() {
        every { ffpCategoriesData.data?.getFulfilmentPointCategories?.edges } returns ffpCategoryEdges
        getFFPCategoriesAnswer()
        fulfilmentPointDataSource.getFulfilmentPointCategories(PAGE_SIZE, PAGE) { error, response ->
            assertEquals(null, error)
            assertEquals(PaginatedObject(ffpCategories, NEXT_PAGE), response)

        }
    }

    @Test
    fun `get Fulfilment Point Categories throws exception`() {

        getFFPCategoriesAnswer()
        every { ffpCategoriesData.data?.getFulfilmentPointCategories?.edges } throws ApolloHttpException(
            any()
        )
        fulfilmentPointDataSource.getFulfilmentPointCategories(PAGE_SIZE, PAGE) { error, response ->
            assert(error is ApolloHttpException)
            assertEquals(null, response)

        }
    }

    @Test
    fun `get Fulfilment Point Categories returns failure`() {
        getFFPCategoriesAnswer(isFailure = true)
        fulfilmentPointDataSource.getFulfilmentPointCategories(PAGE_SIZE, PAGE) { error, response ->
            assert(error is ApolloException)
            assertEquals(null, response)

        }
    }

    @Test
    fun `get Fulfilment Point Categories returns null`() {
        getFFPCategoriesAnswer()
        val callback = { error: Exception?, response: PaginatedObject<FulfilmentPointCategory?>? ->
            assertEquals(null, error)
            assertEquals(PaginatedObject<FulfilmentPointCategory>(null, null), response)

        }
        every { ffpCategoriesData.data?.getFulfilmentPointCategories?.edges } returns null
        fulfilmentPointDataSource.getFulfilmentPointCategories(PAGE_SIZE, PAGE) { error, response ->
            assertEquals(null, error)
            assertEquals(PaginatedObject<FulfilmentPointCategory?>(null, NEXT_PAGE), response)

        }
        every { ffpCategoriesData.data?.getFulfilmentPointCategories?.edges } returns nullableFFPCategoryEdges
        fulfilmentPointDataSource.getFulfilmentPointCategories(PAGE_SIZE, PAGE) { error, response ->
            assertEquals(null, error)
            assertEquals(
                PaginatedObject(nullableFFPCategories, NEXT_PAGE),
                response
            )

        }
        every { ffpCategoriesData.data?.getFulfilmentPointCategories } returns null
        fulfilmentPointDataSource.getFulfilmentPointCategories(PAGE_SIZE, PAGE) { error, response ->
            callback.invoke(error, response)
        }
        every { ffpCategoriesData.data } returns null
        fulfilmentPointDataSource.getFulfilmentPointCategories(PAGE_SIZE, PAGE) { error, response ->
            callback.invoke(error, response)

        }
        every { ffpCategoriesData.errors } returns listOf(Error(""))
        fulfilmentPointDataSource.getFulfilmentPointCategories(PAGE_SIZE, PAGE) { error, response ->
            callback.invoke(error, response)

        }
    }

    private fun getFFPCategoriesAnswer(isFailure: Boolean = false) {
        every { ffpCategoriesData.errors } returns null
        every { ffpCategoriesData.data?.getFulfilmentPointCategories?.nextPage } returns NEXT_PAGE
        every {
            apolloClient.query(
                GetFulfilmentPointCategoriesQuery(PAGE_SIZE, PAGE.toInput())
            ).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build().enqueue(capture(ffpCategoriesSlot))
        } answers {
            if (isFailure) {
                ffpCategoriesSlot.captured.onFailure(ApolloException(""))
            } else {
                ffpCategoriesSlot.captured.onResponse(ffpCategoriesData)
            }
        }
    }

    @Test
    fun `get Fulfilment Point Category returns success`() {
        every { ffpCategoryData.data?.getFulfilmentPointCategory?.fragments?.fragmentFulfilmentPointCategory } returns fragmentFulfilmentPointCategory
        getFFPCategoryAnswer()
        fulfilmentPointDataSource.getFulfilmentPointCategoryById(fulfilmentPointID) { error, response ->
            assertEquals(null, error)
            assertEquals(fulfilmentPointCategory, response)

        }
    }

    @Test
    fun `get Fulfilment Point Category throws exception`() {
        //Given
        getFFPCategoryAnswer()
        every { ffpCategoryData.data?.getFulfilmentPointCategory?.fragments?.fragmentFulfilmentPointCategory }
            .throws(ApolloHttpException(any()))
        //When
        fulfilmentPointDataSource.getFulfilmentPointCategoryById(fulfilmentPointID) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Fulfilment Point Category returns failure`() {
        //Given
        getFFPCategoryAnswer(isFailure = true)
        every { ffpCategoryData.data?.getFulfilmentPointCategory?.fragments?.fragmentFulfilmentPointCategory }
            .throws(ApolloHttpException(any()))
        //When
        fulfilmentPointDataSource.getFulfilmentPointCategoryById(fulfilmentPointID) { error, response ->
            //Then
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Fulfilment Point Category returns null`() {
        getFFPCategoryAnswer()
        val callback = { error: Exception?, response: FulfilmentPointCategory? ->
            assertEquals(null, error)
            assertEquals(null, response)

        }
        every {
            ffpCategoryData.data?.getFulfilmentPointCategory?.fragments?.fragmentFulfilmentPointCategory
        }.returns(null)
        //When
        fulfilmentPointDataSource.getFulfilmentPointCategoryById(fulfilmentPointID) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { ffpCategoryData.data?.getFulfilmentPointCategory?.fragments }.returns(null)
        //When
        fulfilmentPointDataSource.getFulfilmentPointCategoryById(fulfilmentPointID) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { ffpCategoryData.data?.getFulfilmentPointCategory }.returns(null)
        //When
        fulfilmentPointDataSource.getFulfilmentPointCategoryById(fulfilmentPointID) { error, response ->
            //Then
            callback.invoke(error, response)
        }
        every { ffpCategoryData.data }.returns(null)
        //When
        fulfilmentPointDataSource.getFulfilmentPointCategoryById(fulfilmentPointID) { error, response ->
            //Then
            callback.invoke(error, response)
        }

    }

    private fun getFFPCategoryAnswer(isFailure: Boolean = false) {
        every {
            apolloClient.query(
                GetFulfilmentPointCategoryByIdQuery(fulfilmentPointID)
            ).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build().enqueue(capture(ffpCategorySlot))
        } answers {
            if (isFailure) {
                ffpCategorySlot.captured.onFailure(ApolloException(""))
            } else {
                ffpCategorySlot.captured.onResponse(ffpCategoryData)
            }
        }
    }

}