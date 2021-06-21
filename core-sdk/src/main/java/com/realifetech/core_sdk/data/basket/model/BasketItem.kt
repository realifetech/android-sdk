package com.realifetech.core_sdk.data.basket.model

import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.core_sdk.data.product.Product
import com.realifetech.core_sdk.data.product.ProductModifierItemSelection
import com.realifetech.core_sdk.data.product.ProductVariant

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