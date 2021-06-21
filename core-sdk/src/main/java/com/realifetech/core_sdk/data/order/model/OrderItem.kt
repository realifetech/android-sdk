package com.realifetech.core_sdk.data.order.model

import android.os.Parcelable
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.core_sdk.data.product.Product
import com.realifetech.core_sdk.data.product.ProductModifierItemSelection
import com.realifetech.core_sdk.data.product.ProductVariant
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderItem(
    val id: String,
    val product: Product?,
    val productVariant: ProductVariant?,
    val fulfilmentPoint: FulfilmentPoint?,
    val productModifierItems: List<ProductModifierItemSelection?>?,
    val price: Int?,
    val modifierItemsPrice: Int?,
    val quantity: Int?,
    val totalPrice: Int?,
    val title: String?,
    val subTitle: String?,
    val imageUrl: String?
) : Parcelable