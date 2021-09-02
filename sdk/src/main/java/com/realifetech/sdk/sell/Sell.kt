package com.realifetech.sdk.sell

import com.realifetech.sdk.sell.basket.BasketFeature
import com.realifetech.sdk.sell.fulfilmentpoint.FulfilmentPointFeature
import com.realifetech.sdk.sell.order.OrderFeature
import com.realifetech.sdk.sell.payment.PaymentFeature
import com.realifetech.sdk.sell.product.ProductFeature
import com.realifetech.sdk.sell.weboredering.WebOrderingFeature
import kotlinx.coroutines.ExperimentalCoroutinesApi

class Sell(
    private val paymentFeature: PaymentFeature,
    private val productFeature: ProductFeature,
    private val basketFeature: BasketFeature,
    private val orderFeature: OrderFeature,
    private val fulfilmentPointFeature: FulfilmentPointFeature,
    private val webOrderingFeature: WebOrderingFeature
) {
    fun getPayment(): PaymentFeature {
        return paymentFeature
    }

    fun getProduct(): ProductFeature {
        return productFeature
    }

    fun getBasket(): BasketFeature {
        return basketFeature
    }

    fun getOrder(): OrderFeature {
        return orderFeature
    }

    fun getFulfilmentPoint(): FulfilmentPointFeature {
        return fulfilmentPointFeature
    }

    @ExperimentalCoroutinesApi
    fun createOrderingJourneyFragment() {
        return webOrderingFeature.startActivity()
    }

}