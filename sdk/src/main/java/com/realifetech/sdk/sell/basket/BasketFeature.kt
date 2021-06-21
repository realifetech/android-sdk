package com.realifetech.sdk.sell.basket

import com.realifetech.core_sdk.data.basket.model.Basket
import com.realifetech.core_sdk.data.basket.model.BasketRequest
import com.realifetech.core_sdk.data.basket.model.CheckoutRequest
import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.data.shared.`object`.StandardResponse
import com.realifetech.sdk.general.utils.executeCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BasketFeature private constructor() {
    private val basketRepo = BasketProvider().provideBasketRepository()

    fun getMyBasket(callback: (error: Exception?, basket: Basket?) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = basketRepo.getBasket()
            executeCallback(result, callback)
        }
    }

    fun createMyBasket(
        basket: BasketRequest,
        callback: (error: Exception?, basket: Basket?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = basketRepo.createMyBasket(basket)
            executeCallback(result, callback)
        }
    }

    fun updateMyBasket(
        basket: BasketRequest,
        callback: (error: Exception?, basket: Basket?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = basketRepo.updateMyBasket(basket)
            executeCallback(result, callback)
        }
    }

    fun deleteMyBasket(callback: (error: Exception?, response: StandardResponse?) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = basketRepo.deleteMyBasket()
            executeCallback(result, callback)
        }
    }

    fun checkoutMyBasket(
        input: CheckoutRequest,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = basketRepo.checkoutMyBasket(input)
            executeCallback(result, callback)
        }
    }

    private object Holder {
        val instance = BasketFeature()
    }

    companion object {
        val INSTANCE: BasketFeature by lazy { Holder.instance }
    }
}