package com.realifetech.sdk.core.data.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductImage(
    val imageUrl: String?,
    val position: Int?
) : Parcelable