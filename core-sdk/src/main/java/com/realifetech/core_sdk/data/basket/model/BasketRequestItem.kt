package com.realifetech.core_sdk.data.basket.model

data class BasketRequestItem(
    val product: String?,
    val productVariant: String?,
    val fulfilmentPoint: String?,
    val quantity: Int?,
    val productModifierItems: List<ProductModifierItemSelection?>?
)
