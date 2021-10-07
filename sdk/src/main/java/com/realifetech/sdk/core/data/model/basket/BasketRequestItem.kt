package com.realifetech.sdk.core.data.model.basket

data class BasketRequestItem(
    val id: Int,
    val product: String?,
    val productVariant: String?,
    val fulfilmentPoint: String?,
    val quantity: Int?,
    val productModifierItems: List<ProductModifierItemSelectionRequest?>?
)
