package com.realifetech.sdk.core.data.product

import android.os.Parcelable
import com.realifetech.sdk.core.data.shared.translation.StandardTranslation
import com.realifetech.type.ProductModifierItemStatus
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModifierItem(
    val id: String?,
    val status: ProductModifierItemStatus?,
    val externalId: String?,
    val additionalPrice: Int?,
    val position: Int?,
    val translations: List<StandardTranslation?>?
) : Parcelable