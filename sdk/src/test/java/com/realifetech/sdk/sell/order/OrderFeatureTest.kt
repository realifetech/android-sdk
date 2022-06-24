package com.realifetech.sdk.sell.order

import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.order.domain.OrderRepository
import com.realifetech.sdk.sell.order.mocks.OrdersMocks.order1
import com.realifetech.sdk.sell.order.mocks.OrdersMocks.orderUpdateWrapper
import com.realifetech.sdk.sell.order.mocks.OrdersMocks.paginatedObject
import com.realifetech.sdk.sell.payment.PaymentFeatureTest
import com.realifetech.sdk.sell.payment.mocks.PaymentIntentMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class OrderFeatureTest {

    @RelaxedMockK
    private lateinit var orderRepository: OrderRepository
    private lateinit var orderSlot: CapturingSlot<(error: Exception?, order: Order?) -> Unit>
    private lateinit var paginatedObjectSlot: CapturingSlot<(error: Exception?, response: PaginatedObject<Order?>?) -> Unit>
    private lateinit var orderFeature: OrderFeature

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        orderFeature = OrderFeature(orderRepository)
        orderSlot = CapturingSlot()
        paginatedObjectSlot = CapturingSlot()
    }


    @Test
    fun `get orders returns Data`() {
        //Given
        every {
            orderRepository.getOrders(PAGE_SIZE, PAGE, capture(paginatedObjectSlot))
        } answers {
            paginatedObjectSlot.captured.invoke(null, paginatedObject)
        }
        //When
        orderFeature.getOrders(PAGE_SIZE, PAGE) { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(paginatedObject, response)
        }
    }

    @Test
    fun `get orders returns Exception`() {
        //Given
        every {
            orderRepository.getOrders(PAGE_SIZE, PAGE, capture(paginatedObjectSlot))
        } answers {
            paginatedObjectSlot.captured.invoke(Exception(), null)
        }
        //When
        orderFeature.getOrders(PAGE_SIZE, PAGE) { error, response ->
            //Then
            assert(error is Exception)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get order by id returns Data`() {
        //Given
        every {
            orderRepository.getOrderById(ORDER_ID, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(null, order1.asModel)
        }
        //When
        orderFeature.getOrderById(ORDER_ID) { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(order1.asModel, response)
        }
    }

    @Test
    fun `get order by id returns Exception`() {

        //Given
        every {
            orderRepository.getOrderById(ORDER_ID, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(Exception(), null)
        }
        //When
        orderFeature.getOrderById(ORDER_ID) { error, response ->
            //Then
            assert( error is Exception)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `update order returns Data`() {
        //Given
        every {
            orderRepository.updateMyOrder(ORDER_ID, orderUpdateWrapper, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(null, order1.asModel)
        }
        //When
        orderFeature.updateMyOrder(ORDER_ID,orderUpdateWrapper) { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(order1.asModel, response)
        }
    }

    @Test
    fun `update order returns Exception`() {
        //Given
        every {
            orderRepository.updateMyOrder(ORDER_ID, orderUpdateWrapper, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(Exception(), null)
        }
        //When
        orderFeature.updateMyOrder(ORDER_ID, orderUpdateWrapper) { error, response ->
            //Then
            assert( error is Exception)
            Assert.assertEquals(null, response)
        }
    }

    companion object {

        private const val PAGE_SIZE = 10
        private const val PAGE = 2
        private const val ORDER_ID = "1"
    }

}