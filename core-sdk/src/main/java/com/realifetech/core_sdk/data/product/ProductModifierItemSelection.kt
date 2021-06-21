package com.realifetech.core_sdk.data.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModifierItemSelection(
    val productModifierItem: ProductModifierItem?,
    val quantity: Int?,
    val totalPrice: Int?
) : Parcelable