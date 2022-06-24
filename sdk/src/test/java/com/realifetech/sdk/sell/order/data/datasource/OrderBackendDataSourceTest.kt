package com.realifetech.sdk.sell.order.data.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyOrderByIdQuery
import com.realifetech.GetMyOrdersQuery
import com.realifetech.UpdateMyOrderMutation
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.core.data.model.order.wrapper.asInput
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.order.mocks.OrdersMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class OrderBackendDataSourceTest {

    @RelaxedMockK
    private lateinit var apolloClient: ApolloClient
    private lateinit var orderBackendDataSource: OrderBackendDataSource
    private lateinit var getOrdersData: Response<GetMyOrdersQuery.Data>
    private lateinit var getOrdersCapture: CapturingSlot<ApolloCall.Callback<GetMyOrdersQuery.Data>>
    private lateinit var getOrderData: Response<GetMyOrderByIdQuery.Data>
    private lateinit var getOrderCapture: CapturingSlot<ApolloCall.Callback<GetMyOrderByIdQuery.Data>>
    private lateinit var updateOrderData: Response<UpdateMyOrderMutation.Data>
    private lateinit var updateOrderCapture: CapturingSlot<ApolloCall.Callback<UpdateMyOrderMutation.Data>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        initMockedFields()
    }

    private fun initMockedFields() {
        orderBackendDataSource = OrderBackendDataSource(apolloClient)
        // init getOrders mocked response data & capture
        getOrdersData = mockk()
        getOrdersCapture = CapturingSlot()
        // init getOrder mocked response data & capture
        getOrderData = mockk()
        getOrderCapture = CapturingSlot()
        // init getOrder mocked response data & capture
        updateOrderData = mockk()
        updateOrderCapture = CapturingSlot()
    }

    @Test
    fun `get orders results success`() = runBlocking {
        //Given
        every {
            getOrdersData.data?.getMyOrders
        } returns OrdersMocks.ordersResponse
        getOrdersSuccessAnswer()
        // When
        orderBackendDataSource.getOrders(PAGE_SIZE, PAGE) { error, response ->
            // Then
            Assert.assertEquals(
                PaginatedObject(
                    OrdersMocks.ordersResponse.edges?.map { it?.fragments?.fragmentOrder?.asModel },
                    NEXT_PAGE
                ), response
            )
            Assert.assertEquals(null, error)
        }

    }

    @Test
    fun `get orders results with throwable`() = runBlocking {
        //Given
        every {
            getOrdersData.data?.getMyOrders
        } throws ApolloHttpException(ArgumentMatchers.any())
        getOrdersSuccessAnswer()
        // When
        orderBackendDataSource.getOrders(PAGE_SIZE, PAGE) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get orders results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.query(GetMyOrdersQuery(PAGE_SIZE, PAGE.toInput())).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
                .enqueue(capture(getOrdersCapture))
        } answers { getOrdersCapture.captured.onFailure(ApolloException("Error")) }
        // When
        orderBackendDataSource.getOrders(PAGE_SIZE, PAGE) { error, response ->
            // Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }
    }


    @Test
    fun `get orders results with null response`() = runBlocking {
        // Given
        val errorCallback = { error: Exception?, response: PaginatedObject<Order?>? ->
            // Then
            Assert.assertSame(null, error)
            Assert.assertSame(null, response)
        }
        getOrdersSuccessAnswer()
        // When
        every {
            getOrdersData.data?.getMyOrders?.nextPage
        } returns NEXT_PAGE
        every {
            getOrdersData.data?.getMyOrders?.edges
        } returns null
        orderBackendDataSource.getOrders(PAGE_SIZE, PAGE) { error, response ->
            // Then
            val expected: PaginatedObject<Order?> = PaginatedObject(null, NEXT_PAGE)
            Assert.assertEquals(null, error)
            Assert.assertEquals(expected, response)
        }

        every {
            getOrdersData.data?.getMyOrders?.edges
        } returns OrdersMocks.orderResponseWithEmptyEdge
        orderBackendDataSource.getOrders(PAGE_SIZE, PAGE) { error, response ->
            // Then
            val expected: PaginatedObject<Order?> = PaginatedObject(
                OrdersMocks.wrappedOrdersWithEmptyEdge, NEXT_PAGE
            )
            Assert.assertEquals(null, error)
            Assert.assertEquals(expected, response)
        }


        every {
            getOrdersData.data?.getMyOrders
        } returns null
        orderBackendDataSource.getOrders(
            PAGE_SIZE, PAGE
        ) { error, response ->
            // Then
            errorCallback(error, response)
        }

        every {
            getOrdersData.data
        } returns null
        orderBackendDataSource.getOrders(
            PAGE_SIZE, PAGE
        ) { error, response ->
            // Then
            errorCallback(error, response)
        }
    }

    @Test
    fun `get Order By Id results success`() {
        //Given
        every {
            getOrderData.data?.getMyOrder?.fragments?.fragmentOrder
        } returns OrdersMocks.order1
        getOrderSuccessAnswer()
        // When
        orderBackendDataSource.getOrderById(ORDER_ID) { error, response ->
            // Then
            Assert.assertEquals(OrdersMocks.order1.asModel, response)
            Assert.assertEquals(null, error)
        }
    }

    @Test
    fun `get order results with throwable`() = runBlocking {
        //Given
        every {
            getOrderData.data?.getMyOrder?.fragments?.fragmentOrder
        } throws ApolloHttpException(ArgumentMatchers.any())
        getOrderSuccessAnswer()
        // When
        orderBackendDataSource.getOrderById(ORDER_ID) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get order results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.query(GetMyOrderByIdQuery(ORDER_ID)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
                .enqueue(capture(getOrderCapture))
        } answers { getOrderCapture.captured.onFailure(ApolloException("Error")) }
        // When
        orderBackendDataSource.getOrderById(ORDER_ID) { error, response ->
            // Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get order results with null response`() = runBlocking {
        // Given
        val callback = { error: Exception?, response: Order? ->
            // Then
            Assert.assertSame(null, error)
            Assert.assertSame(null, response)
        }
        getOrderSuccessAnswer()

        // When
        every {
            getOrderData.data?.getMyOrder?.fragments?.fragmentOrder
        } returns null
        orderBackendDataSource.getOrderById(ORDER_ID) { error, response ->
            // Then
            callback(error, response)
        }

        every {
            getOrderData.data?.getMyOrder?.fragments
        } returns null
        orderBackendDataSource.getOrderById(ORDER_ID) { error, response ->
            // Then
            callback(error, response)
        }

        every {
            getOrderData.data?.getMyOrder
        } returns null
        orderBackendDataSource.getOrderById(
            ORDER_ID
        ) { error, response ->
            // Then
            callback(error, response)
        }
        every {
            getOrderData.data
        } returns null
        orderBackendDataSource.getOrderById(
            ORDER_ID
        ) { error, response ->
            // Then
            callback(error, response)
        }
    }


    @Test
    fun `update My Order results success`() {
        //Given
        every {
            updateOrderData.data?.updateMyOrder?.fragments?.fragmentOrder
        } returns OrdersMocks.order1
        updateOrderSuccessAnswer()
        // When
        orderBackendDataSource.updateMyOrder(
            ORDER_ID,
            OrdersMocks.orderUpdateWrapper
        ) { error, response ->
            // Then
            Assert.assertEquals(
                OrdersMocks.order1.asModel, response
            )
            Assert.assertEquals(null, error)
        }
    }

    @Test
    fun `update order results with throwable`() = runBlocking {
        //Given
        every {
            updateOrderData.data?.updateMyOrder?.fragments?.fragmentOrder
        } throws ApolloHttpException(ArgumentMatchers.any())
        updateOrderSuccessAnswer()
        // When
        orderBackendDataSource.updateMyOrder(
            ORDER_ID,
            OrdersMocks.orderUpdateWrapper
        ) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `update order results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.mutate(
                UpdateMyOrderMutation(ORDER_ID, OrdersMocks.orderUpdateWrapper.asInput)
            ).enqueue(capture(updateOrderCapture))
        } answers { updateOrderCapture.captured.onFailure(ApolloException("Error")) }
        // When
        orderBackendDataSource.updateMyOrder(
            ORDER_ID,
            OrdersMocks.orderUpdateWrapper
        ) { error, response ->
            // Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `update order results with null response`() = runBlocking {
        // Given
        val callback = { error: Exception?, response: Order? ->
            // Then
            Assert.assertSame(null, error)
            Assert.assertSame(null, response)
        }
        updateOrderSuccessAnswer()

        // When
        every {
            updateOrderData.data?.updateMyOrder?.fragments?.fragmentOrder
        } returns null
        orderBackendDataSource.updateMyOrder(
            ORDER_ID,
            OrdersMocks.orderUpdateWrapper
        ) { error, response ->
            // Then
            callback(error, response)
        }

        every {
            updateOrderData.data?.updateMyOrder?.fragments
        } returns null
        orderBackendDataSource.updateMyOrder(
            ORDER_ID,
            OrdersMocks.orderUpdateWrapper
        ) { error, response ->
            // Then
            callback(error, response)
        }
        // Given
        every {
            updateOrderData.data?.updateMyOrder
        } returns null
        // When
        orderBackendDataSource.updateMyOrder(
            ORDER_ID,
            OrdersMocks.orderUpdateWrapper
        ) { error, response ->
            // Then
            callback(error, response)
        }
        //Given
        every {
            updateOrderData.data
        } returns null
        // When
        orderBackendDataSource.updateMyOrder(
            ORDER_ID,
            OrdersMocks.orderUpdateWrapper
        ) { error, response ->
            // Then
            callback(error, response)
        }
    }

    private fun updateOrderSuccessAnswer() {
        every {
            apolloClient.mutate(
                UpdateMyOrderMutation(
                    ORDER_ID,
                    OrdersMocks.orderUpdateWrapper.asInput
                )
            )
                .enqueue(capture(updateOrderCapture))
        } answers {
            updateOrderCapture.captured.onResponse(updateOrderData)
        }
    }

    private fun getOrdersSuccessAnswer() {
        every {
            apolloClient.query(GetMyOrdersQuery(PAGE_SIZE, PAGE.toInput())).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
                .enqueue(capture(getOrdersCapture))
        } answers {
            getOrdersCapture.captured.onResponse(getOrdersData)
        }
    }

    private fun getOrderSuccessAnswer() {
        every {
            apolloClient.query(GetMyOrderByIdQuery(ORDER_ID))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                .build()
                .enqueue(capture(getOrderCapture))
        } answers {
            getOrderCapture.captured.onResponse(getOrderData)
        }
    }

    companion object {

        private const val PAGE_SIZE = 10
        private const val PAGE = 2
        private const val NEXT_PAGE = 3
        private const val ORDER_ID = "1"
    }
}