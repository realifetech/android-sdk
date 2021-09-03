package com.realifetech.sdk.sell.basket

import com.realifetech.sdk.core.data.model.basket.Basket
import com.realifetech.sdk.core.data.model.basket.BasketRequest
import com.realifetech.sdk.core.data.model.basket.CheckoutRequest
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse
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