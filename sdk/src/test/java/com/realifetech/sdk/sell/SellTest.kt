package com.realifetech.sdk.sell

import com.realifetech.sdk.sell.basket.BasketFeature
import com.realifetech.sdk.sell.fulfilmentpoint.FulfilmentPointFeature
import com.realifetech.sdk.sell.order.OrderFeature
import com.realifetech.sdk.sell.payment.PaymentFeature
import com.realifetech.sdk.sell.product.ProductFeature
import com.realifetech.sdk.sell.weboredering.WebOrderingFeature
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SellTest {

    @RelaxedMockK
    private lateinit var basketFeature: BasketFeature

    @RelaxedMockK
    private lateinit var fulfilmentPointFeature: FulfilmentPointFeature

    @RelaxedMockK
    private lateinit var orderFeature: OrderFeature

    @RelaxedMockK
    private lateinit var paymentFeature: PaymentFeature

    @RelaxedMockK
    private lateinit var productFeature: ProductFeature

    @RelaxedMockK
    private lateinit var webOrderingFeature: WebOrderingFeature

    private lateinit var sell: Sell

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sell = Sell(
            paymentFeature,
            productFeature,
            basketFeature,
            orderFeature,
            fulfilmentPointFeature,
            webOrderingFeature
        )
    }

    @Test
    fun getPayment() {
        val payment = sell.getPayment()
        assertEquals(paymentFeature, payment)
    }

    @Test
    fun getProduct() {
        val product = sell.getProduct()
        assertEquals(productFeature, product)

    }

    @Test
    fun getBasket() {
        val basket = sell.getBasket()
        assertEquals(basketFeature, basket)
    }

    @Test
    fun getOrder() {
        val order = sell.getOrder()
        assertEquals(orderFeature, order)
    }

    @Test
    fun getFulfilmentPoint() {
        val fulfilmentPoint = sell.getFulfilmentPoint()
        assertEquals(fulfilmentPointFeature, fulfilmentPoint)
    }

    @Test
    fun createOrderingJourneyFragment() {
        sell.createOrderingJourneyFragment()
        verify(exactly = 1) { webOrderingFeature.startActivity() }
    }
}