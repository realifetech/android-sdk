package com.realifetech.sdk.sell.basket

import com.realifetech.sdk.core.data.model.basket.Basket
import com.realifetech.sdk.core.data.model.basket.BasketRequest
import com.realifetech.sdk.core.data.model.basket.CheckoutRequest
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse
import com.realifetech.sdk.sell.basket.domain.BasketRepository
import javax.inject.Inject

class BasketFeature @Inject constructor(private val basketRepo: BasketRepository) {

    suspend fun getMyBasket(): Basket? {
        return basketRepo.getBasket()
    }

    suspend fun createMyBasket(basket: BasketRequest): Basket? {
        return basketRepo.createMyBasket(basket)
    }

    suspend fun updateMyBasket(basket: BasketRequest): Basket? {
        return basketRepo.updateMyBasket(basket)
    }

    suspend fun deleteMyBasket(): StandardResponse {
        return basketRepo.deleteMyBasket()
    }

    suspend fun checkoutMyBasket(input: CheckoutRequest): Order? {
        return basketRepo.checkoutMyBasket(input)
    }
}