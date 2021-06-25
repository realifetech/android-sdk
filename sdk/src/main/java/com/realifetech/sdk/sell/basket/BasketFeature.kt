package com.realifetech.sdk.sell.basket

import com.realifetech.core_sdk.data.basket.model.Basket
import com.realifetech.core_sdk.data.basket.model.BasketRequest
import com.realifetech.core_sdk.data.basket.model.CheckoutRequest
import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.data.shared.`object`.StandardResponse

class BasketFeature private constructor() {
    private val basketRepo = BasketProvider().provideBasketRepository()

    fun getMyBasket(callback: (error: Exception?, basket: Basket?) -> Unit) {
        basketRepo.getBasket(callback)
    }

    fun createMyBasket(
        basket: BasketRequest,
        callback: (error: Exception?, basket: Basket?) -> Unit
    ) {
        basketRepo.createMyBasket(basket, callback)
    }

    fun updateMyBasket(
        basket: BasketRequest,
        callback: (error: Exception?, basket: Basket?) -> Unit
    ) {
        basketRepo.updateMyBasket(basket, callback)
    }

    fun deleteMyBasket(callback: (error: Exception?, response: StandardResponse?) -> Unit) {
        basketRepo.deleteMyBasket(callback)
    }

    fun checkoutMyBasket(
        input: CheckoutRequest,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        basketRepo.checkoutMyBasket(input, callback)
    }

    private object Holder {
        val instance = BasketFeature()
    }

    companion object {
        val INSTANCE: BasketFeature by lazy { Holder.instance }
    }
}