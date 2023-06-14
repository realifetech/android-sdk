package com.realifetech.sdk.sell.basket.data

import com.realifetech.sdk.core.data.model.basket.Basket
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse
import com.realifetech.type.BasketInput
import com.realifetech.type.CheckoutInput

interface BasketDataSource {
    suspend fun getBasket(): Basket?
    suspend fun createMyBasket(basketInput: BasketInput): Basket?
    suspend fun updateMyBasket(basketInput: BasketInput): Basket?
    suspend fun deleteMyBasket(): StandardResponse
    suspend fun checkoutMyBasket(checkoutInput: CheckoutInput): Order?
}