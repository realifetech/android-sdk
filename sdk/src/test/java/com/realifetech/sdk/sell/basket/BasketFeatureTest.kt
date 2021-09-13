package com.realifetech.sdk.sell.basket

import com.realifetech.sdk.core.data.model.basket.Basket
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse
import com.realifetech.sdk.sell.basket.domain.BasketRepository
import com.realifetech.sdk.sell.basket.mocks.BasketMocks
import com.realifetech.sdk.sell.order.mocks.OrdersMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BasketFeatureTest {

    @RelaxedMockK
    private lateinit var basketRepository: BasketRepository
    private lateinit var basketSlot: CapturingSlot<(error: Exception?, order: Basket?) -> Unit>
    private lateinit var orderSlot: CapturingSlot<(error: Exception?, order: Order?) -> Unit>
    private lateinit var responseSlot: CapturingSlot<(error: Exception?, order: StandardResponse?) -> Unit>
    private lateinit var basketFeature: BasketFeature

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        basketFeature = BasketFeature(basketRepository)
        basketSlot = CapturingSlot()
        orderSlot = CapturingSlot()
        responseSlot = CapturingSlot()
    }


    @Test
    fun `get my basket returns Data`() {
        //Given
        every {
            basketRepository.getBasket(capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(null, BasketMocks.basket)
        }
        //When
        basketFeature.getMyBasket { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(BasketMocks.basket, response)
        }
    }

    @Test
    fun `get my basket returns Exception`() {
        //Given
        every {
            basketRepository.getBasket(capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(Exception(), null)
        }
        //When
        basketFeature.getMyBasket { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }

    @Test
    fun `create My Basket returns Data`() {
        //Given
        every {
            basketRepository.createMyBasket(BasketMocks.basketRequest, capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(null, BasketMocks.basket)
        }
        //When
        basketFeature.createMyBasket(BasketMocks.basketRequest) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(BasketMocks.basket, response)
        }
    }

    @Test
    fun `create My Basket returns  Exception`() {
        //Given
        every {
            basketRepository.createMyBasket(BasketMocks.basketRequest, capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(Exception(), null)
        }
        //When
        basketFeature.createMyBasket(BasketMocks.basketRequest) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }

    @Test
    fun `checkout My Basket property returns Data`() {
        //Given
        every {
            basketRepository.checkoutMyBasket(BasketMocks.checkoutRequest, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(null, OrdersMocks.order1.asModel)
        }
        //When
        basketFeature.checkoutMyBasket(BasketMocks.checkoutRequest) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(OrdersMocks.order1.asModel, response)
        }
    }

    @Test
    fun `checkout My Basket property returns  Exception`() {
        //Given
        every {
            basketRepository.checkoutMyBasket(BasketMocks.checkoutRequest, capture(orderSlot))
        } answers {
            orderSlot.captured.invoke(Exception(), null)
        }
        //When
        basketFeature.checkoutMyBasket(BasketMocks.checkoutRequest) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }


    @Test
    fun `update My Basket property returns Data`() {
        //Given
        every {
            basketRepository.updateMyBasket(BasketMocks.basketRequest, capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(null, BasketMocks.basket)
        }
        //When
        basketFeature.updateMyBasket(BasketMocks.basketRequest) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(BasketMocks.basket, response)
        }
    }

    @Test
    fun `update My Basket property returns  Exception`() {
        //Given
        every {
            basketRepository.updateMyBasket(BasketMocks.basketRequest, capture(basketSlot))
        } answers {
            basketSlot.captured.invoke(Exception(), null)
        }
        //When
        basketFeature.updateMyBasket(BasketMocks.basketRequest) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }

    }


    @Test
    fun `delete My Basket property returns Data`() {
        //Given
        every {
            basketRepository.deleteMyBasket(capture(responseSlot))
        } answers {
            responseSlot.captured.invoke(null, BasketMocks.response)
        }
        //When
        basketFeature.deleteMyBasket { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(BasketMocks.response, response)
        }
    }

    @Test
    fun `delete My Basket property returns  Exception`() {
        //Given
        every {
            basketRepository.deleteMyBasket(capture(responseSlot))
        } answers {
            responseSlot.captured.invoke(Exception(), null)
        }
        //When
        basketFeature.deleteMyBasket { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }

    }

}