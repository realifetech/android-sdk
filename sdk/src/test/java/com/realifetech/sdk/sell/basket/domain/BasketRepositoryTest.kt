package com.realifetech.sdk.sell.basket.domain

import com.realifetech.sdk.core.data.model.basket.Basket
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse
import com.realifetech.sdk.sell.basket.data.BasketDataSource
import com.realifetech.sdk.sell.basket.mocks.BasketMocks
import com.realifetech.sdk.sell.order.mocks.OrdersMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class BasketRepositoryTest {


    @RelaxedMockK
    private lateinit var basketDataSource: BasketDataSource
    private lateinit var basketSlot: CapturingSlot<(error: Exception?, order: Basket?) -> Unit>
    private lateinit var orderSlot: CapturingSlot<(error: Exception?, order: Order?) -> Unit>
    private lateinit var responseSlot: CapturingSlot<(error: Exception?, order: StandardResponse?) -> Unit>
    private lateinit var basketRepository: BasketRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        basketRepository = BasketRepository(basketDataSource)
        basketSlot = CapturingSlot()
        orderSlot = CapturingSlot()
        responseSlot = CapturingSlot()
    }


    @Test
    fun `get my basket returns Data`() {
        //Given
        every {
            basketDataSource.getBasket(capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(null, BasketMocks.basket)
        }
        //When
        basketRepository.getBasket { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(BasketMocks.basket, response)
        }
    }

    @Test
    fun `get my basket returns Exception`() {
        //Given
        every {
            basketDataSource.getBasket(capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(Exception(), null)
        }
        //When
        basketRepository.getBasket() { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }

    @Test
    fun `create My Basket returns Data`() {
        //Given
        every {
            basketDataSource.createMyBasket(BasketMocks.basketRequestInput, capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(null, BasketMocks.basket)
        }
        //When
        basketRepository.createMyBasket(BasketMocks.basketRequest) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(BasketMocks.basket, response)
        }
    }

    @Test
    fun `create My Basket returns  Exception`() {
        //Given
        every {
            basketDataSource.createMyBasket(BasketMocks.basketRequestInput, capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(Exception(), null)
        }
        //When
        basketRepository.createMyBasket(BasketMocks.basketRequest) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }

    @Test
    fun `checkout My Basket property returns Data`() {
        //Given
        every {
            basketDataSource.checkoutMyBasket(BasketMocks.checkoutRequestInput, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(null, OrdersMocks.order1.asModel)
        }
        //When
        basketRepository.checkoutMyBasket(BasketMocks.checkoutRequest) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(OrdersMocks.order1.asModel, response)
        }
    }

    @Test
    fun `checkout My Basket property returns  Exception`() {
        //Given
        every {
            basketDataSource.checkoutMyBasket(BasketMocks.checkoutRequestInput, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(Exception(), null)
        }
        //When
        basketRepository.checkoutMyBasket(BasketMocks.checkoutRequest) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }


    @Test
    fun `update My Basket property returns Data`() {
        //Given
        every {
            basketDataSource.updateMyBasket(BasketMocks.basketRequestInput, capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(null, BasketMocks.basket)
        }
        //When
        basketRepository.updateMyBasket(BasketMocks.basketRequest) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(BasketMocks.basket, response)
        }
    }

    @Test
    fun `update My Basket property returns  Exception`() {
        //Given
        every {
            basketDataSource.updateMyBasket(BasketMocks.basketRequestInput, capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(Exception(), null)
        }
        //When
        basketRepository.updateMyBasket(BasketMocks.basketRequest) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }

    }


    @Test
    fun `delete My Basket property returns Data`() {
        //Given
        every {
            basketDataSource.deleteMyBasket(capture(responseSlot))
        } answers {
            responseSlot.captured.invoke(null, BasketMocks.response)
        }
        //When
        basketRepository.deleteMyBasket { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(BasketMocks.response, response)
        }
    }

    @Test
    fun `delete My Basket property returns  Exception`() {
        //Given
        every {
            basketDataSource.deleteMyBasket(capture(responseSlot))
        } answers {
            responseSlot.captured.invoke(Exception(), null)
        }
        //When
        basketRepository.deleteMyBasket { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }

    }
}