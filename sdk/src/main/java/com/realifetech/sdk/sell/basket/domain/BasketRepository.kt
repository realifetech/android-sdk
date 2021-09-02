package com.realifetech.sdk.sell.basket.domain

import com.realifetech.sdk.core.data.basket.model.*
import com.realifetech.sdk.core.data.order.model.Order
import com.realifetech.sdk.core.data.shared.`object`.StandardResponse
import com.realifetech.type.BasketInput
import com.realifetech.type.CheckoutInput
import javax.inject.Inject

class BasketRepository @Inject constructor(private val dataSource: DataSource) {

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

    fun deleteMyBasket(callback: (error: Exception?, response: StandardResponse?) -> Unit) =
        dataSource.deleteMyBasket(callback)

    fun checkoutMyBasket(
        checkoutRequest: CheckoutRequest,
        callback: (error: Exception?, order: Order?) -> Unit
    ) =
        dataSource.checkoutMyBasket(checkoutRequest.asInput, callback)

    interface DataSource {
        fun getBasket(callback: (error: Exception?, basket: Basket?) -> Unit)
        fun createMyBasket(
            basketInput: BasketInput,
            callback: (error: Exception?, basket: Basket?) -> Unit
        )

        fun updateMyBasket(
            basketInput: BasketInput,
            callback: (error: Exception?, basket: Basket?) -> Unit
        )

        fun deleteMyBasket(callback: (error: Exception?, response: StandardResponse?) -> Unit)
        fun checkoutMyBasket(
            checkoutInput: CheckoutInput,
            callback: (error: Exception?, order: Order?) -> Unit
        )
    }
}