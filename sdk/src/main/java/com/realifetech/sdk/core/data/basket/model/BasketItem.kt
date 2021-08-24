package com.realifetech.sdk.core.data.basket.model

import com.realifetech.sdk.core.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.product.Product
import com.realifetech.sdk.core.data.product.ProductModifierItemSelection
import com.realifetech.sdk.core.data.product.ProductVariant

data class BasketItem(
    val id: String,
    val price: Int?,
    val modifierItemsPrice: Int?,
    val quantity: Int?,
    val totalPrice: Int?,
    val product: Product?,
    val productVariant: ProductVariant?,
    val fulfilmentPoint: FulfilmentPoint?,
    val productModifierItems: List<ProductModifierItemSelection?>?
)