package com.realifetech.sdk.sell.basket

import com.realifetech.sdk.core.data.basket.model.Basket
import com.realifetech.sdk.core.data.basket.model.BasketRequest
import com.realifetech.sdk.core.data.basket.model.CheckoutRequest
import com.realifetech.sdk.core.data.order.model.Order
import com.realifetech.sdk.core.data.shared.`object`.StandardResponse
import com.realifetech.sdk.sell.basket.domain.BasketRepository
import javax.inject.Inject

class BasketFeature @Inject constructor(private val basketRepo: BasketRepository) {

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
}