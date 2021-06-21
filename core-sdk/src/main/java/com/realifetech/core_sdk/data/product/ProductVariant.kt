package com.realifetech.core_sdk.data.product

import android.os.Parcelable
import com.realifetech.core_sdk.data.shared.translation.HasTranslation
import com.realifetech.core_sdk.data.shared.translation.StandardTranslation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductVariant(
    val id: String,
    val externalId: String?,
    val price: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    override val translations: List<StandardTranslation?>?
) : Parcelable, HasTranslation<StandardTranslation?>