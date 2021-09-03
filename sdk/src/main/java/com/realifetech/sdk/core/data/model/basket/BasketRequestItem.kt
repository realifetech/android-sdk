package com.realifetech.sdk.core.data.model.basket

data class BasketRequestItem(
    val product: String?,
    val productVariant: String?,
    val fulfilmentPoint: String?,
    val quantity: Int?,
    val productModifierItems: List<ProductModifierItemSelection?>?
)
