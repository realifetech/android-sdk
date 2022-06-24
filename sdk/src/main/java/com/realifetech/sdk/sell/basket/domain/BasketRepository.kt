package com.realifetech.sdk.sell.basket.domain

import com.realifetech.sdk.core.data.model.basket.*
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse
import com.realifetech.sdk.sell.basket.data.BasketDataSource
import javax.inject.Inject

class BasketRepository @Inject constructor(private val dataSource: BasketDataSource) {

    fun getBasket(callback: (error: Exception?, basket: Basket?) -> Unit) {
        dataSource.getBasket(callback)
    }

    fun createMyBasket(
        basket: BasketRequest,
        callback: (error: Exception?, basket: Basket?) -> Unit
    ) {
        dataSource.createMyBasket(basket.asInputObject, callback)
    }

    fun updateMyBasket(
        basket: BasketRequest,
        callback: (error: Exception?, basket: Basket?) -> Unit
    ) {
        dataSource.updateMyBasket(basket.asInputObject, callback)
    }

    fun deleteMyBasket(callback: (error: Exception?, response: StandardResponse?) -> Unit) {
        dataSource.deleteMyBasket(callback)
    }

    fun checkoutMyBasket(
        checkoutRequest: CheckoutRequest,
        callback: (error: Exception?, order: Order?) -> Unit
    ) =
        dataSource.checkoutMyBasket(checkoutRequest.asInput, callback)
}