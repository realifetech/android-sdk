package com.realifetech.sdk.core.data.model.basket

import android.os.Parcelable
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.product.ProductModifierItemSelection
import com.realifetech.sdk.core.data.model.product.ProductVariant
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable