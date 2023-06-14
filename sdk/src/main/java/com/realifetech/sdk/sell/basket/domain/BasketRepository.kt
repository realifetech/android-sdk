package com.realifetech.sdk.sell.basket.domain

import com.realifetech.sdk.core.data.model.basket.*
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse
import com.realifetech.sdk.sell.basket.data.BasketDataSource
import javax.inject.Inject

class BasketRepository @Inject constructor(private val dataSource: BasketDataSource) {

    suspend fun getBasket(): Basket? {
        return dataSource.getBasket()
    }

    suspend fun createMyBasket(basket: BasketRequest): Basket? {
        return dataSource.createMyBasket(basket.asInputObject)
    }

    suspend fun updateMyBasket(basket: BasketRequest): Basket? {
        return dataSource.updateMyBasket(basket.asInputObject)
    }

    suspend fun deleteMyBasket(): StandardResponse {
        return dataSource.deleteMyBasket()
    }

    suspend fun checkoutMyBasket(checkoutRequest: CheckoutRequest): Order? {
        return dataSource.checkoutMyBasket(checkoutRequest.asInput)
    }
}