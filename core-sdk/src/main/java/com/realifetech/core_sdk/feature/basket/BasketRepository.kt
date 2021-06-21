package com.realifetech.core_sdk.feature.basket

import com.realifetech.core_sdk.data.basket.model.*
import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.data.shared.`object`.StandardResponse
import com.realifetech.core_sdk.domain.Result
import com.realifetech.type.BasketInput
import com.realifetech.type.CheckoutInput

class BasketRepository(private val dataSource: DataSource) {

    suspend fun getBasket() =
        dataSource.getBasket()

    suspend fun createMyBasket(basket: BasketRequest) =
        dataSource.createMyBasket(basket.asInputObject)

    suspend fun updateMyBasket(basket: BasketRequest) =
        dataSource.updateMyBasket(basket.asInputObject)

    suspend fun deleteMyBasket() = dataSource.deleteMyBasket()

    suspend fun checkoutMyBasket(checkoutRequest: CheckoutRequest) =
        dataSource.checkoutMyBasket(checkoutRequest.asInput)

    interface DataSource {
        suspend fun getBasket(): Result<Basket>
        suspend fun createMyBasket(basketInput: BasketInput): Result<Basket>
        suspend fun updateMyBasket(basketInput: BasketInput): Result<Basket>
        suspend fun deleteMyBasket(): Result<StandardResponse>
        suspend fun checkoutMyBasket(checkoutInput: CheckoutInput): Result<Order>
    }
}