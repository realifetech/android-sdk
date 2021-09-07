package com.realifetech.sdk.sell.order.domain

import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.order.OrderFeature
import com.realifetech.sdk.sell.order.data.database.OrdersSharedPreferencesManager
import com.realifetech.sdk.sell.order.data.datasource.OrderBackendDataSource
import com.realifetech.sdk.sell.order.mocks.OrdersMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class OrderRepositoryTest {


    @RelaxedMockK
    private lateinit var orderDataSource: OrderBackendDataSource
    @RelaxedMockK
    private lateinit var orderLocalStorage: OrdersSharedPreferencesManager
    private lateinit var orderSlot: CapturingSlot<(error: Exception?, order: Order?) -> Unit>
    private lateinit var paginatedObjectSlot: CapturingSlot<(error: Exception?, response: PaginatedObject<Order?>?) -> Unit>
    private lateinit var orderRepository: OrderRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        orderRepository = OrderRepository(orderDataSource,orderLocalStorage)
        orderSlot = CapturingSlot()
        paginatedObjectSlot = CapturingSlot()
    }

    @Test
    fun `get orders returns Data`() {
        //Given
        every {
            orderDataSource.getOrders(PAGE_SIZE, PAGE, capture(paginatedObjectSlot))
        } answers {
            paginatedObjectSlot.captured.invoke(null, OrdersMocks.paginatedObject)
        }
        //When
        orderRepository.getOrders(PAGE_SIZE, PAGE) { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(OrdersMocks.paginatedObject, response)
        }
    }

    @Test
    fun `get orders returns Exception`() {
        //Given
        every {
            orderDataSource.getOrders(PAGE_SIZE, PAGE, capture(paginatedObjectSlot))
        } answers {
            paginatedObjectSlot.captured.invoke(Exception(), null)
        }
        //When
        orderRepository.getOrders(PAGE_SIZE, PAGE) { error, response ->
            //Then
            assert(error is Exception)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get order by id returns Data`() {
        //Given
        every {
            orderDataSource.getOrderById(ORDER_ID, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(null, OrdersMocks.order1.asModel)
        }
        //When
        orderRepository.getOrderById(ORDER_ID) { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(OrdersMocks.order1.asModel, response)
        }
    }

    @Test
    fun `get order by id returns Exception`() {

        //Given
        every {
            orderDataSource.getOrderById(ORDER_ID, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(Exception(), null)
        }
        //When
        orderRepository.getOrderById(ORDER_ID) { error, response ->
            //Then
            assert(error is Exception)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `update order returns Data`() {
        //Given
        every {
            orderDataSource.updateMyOrder(
                ORDER_ID,
                OrdersMocks.orderUpdateWrapper,
                capture(orderSlot)
            )
        } answers {
            orderSlot.captured.invoke(null, OrdersMocks.order1.asModel)
        }
        //When
        orderRepository.updateMyOrder(ORDER_ID, OrdersMocks.orderUpdateWrapper) { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(OrdersMocks.order1.asModel, response)
        }
    }

    @Test
    fun `update order returns Exception`() {
        //Given
        every {
            orderDataSource.updateMyOrder(
                ORDER_ID,
                OrdersMocks.orderUpdateWrapper,
                capture(orderSlot)
            )
        } answers {
            orderSlot.captured.invoke(Exception(), null)
        }
        //When
        orderRepository.updateMyOrder(ORDER_ID, OrdersMocks.orderUpdateWrapper) { error, response ->
            //Then
            assert(error is Exception)
            Assert.assertEquals(null, response)
        }
    }

    companion object {

        private const val PAGE_SIZE = 10
        private const val PAGE = 2
        private const val ORDER_ID = "1"
    }

}