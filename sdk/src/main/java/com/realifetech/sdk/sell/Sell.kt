package com.realifetech.sdk.sell

import com.realifetech.sdk.sell.domain.SellConfiguration
import com.realifetech.sdk.sell.basket.BasketFeature
import com.realifetech.sdk.sell.fulfilmentpoint.FulfilmentPointFeature
import com.realifetech.sdk.sell.order.OrderFeature
import com.realifetech.sdk.sell.payment.PaymentFeature
import com.realifetech.sdk.sell.product.ProductFeature
import com.realifetech.sdk.sell.weboredering.WebOrderingFeature

object Sell {

    val configuration = SellConfiguration()

    fun getPayment(): PaymentFeature {
        return PaymentFeature.INSTANCE
    }

    fun getProduct(): ProductFeature {
        return ProductFeature.INSTANCE
    }

    fun getBasket(): BasketFeature {
        return BasketFeature.INSTANCE
    }

    fun getOrder(): OrderFeature {
        return OrderFeature.INSTANCE
    }

    fun getFulfilmentPoint(): FulfilmentPointFeature {
        return FulfilmentPointFeature.INSTANCE
    }

    fun createOrderingJourneyFragment() {
        return WebOrderingFeature.startActivity()
    }

    private object Holder {
        val instance = Sell
    }

    val INSTANCE: Sell by lazy { Holder.instance }
}