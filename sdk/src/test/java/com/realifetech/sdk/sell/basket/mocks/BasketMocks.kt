package com.realifetech.sdk.sell.basket.mocks

import com.realifetech.sdk.core.data.model.basket.*
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse

object BasketMocks {
    val response = StandardResponse(200, "Success", "deleted successfully")
    val basketItem1 = generateBasketItem("1")
    val checkoutRequest = CheckoutRequest(null, "")
    private fun generateBasketItemRequest() = BasketRequestItem(null, null, null, null, null)
    val basketRequest =
        BasketRequest(null, null, mutableListOf(generateBasketItemRequest()), null, null, null)

    val basketRequestInput = basketRequest.asInputObject
    val checkoutRequestInput= checkoutRequest.asInput
    private fun generateBasketItem(itemId: String) =
        BasketItem(itemId, 50, 2, 1, 52, null, null, null, null)

    val basketItems = listOf(basketItem1, generateBasketItem("2"))
    val basket = Basket(200, 100, 90, null, null, null, null, basketItems)
}