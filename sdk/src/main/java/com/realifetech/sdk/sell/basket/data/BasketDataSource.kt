package com.realifetech.sdk.sell.basket.data

import com.realifetech.sdk.core.data.model.basket.Basket
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse
import com.realifetech.type.BasketInput
import com.realifetech.type.CheckoutInput

interface BasketDataSource {
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