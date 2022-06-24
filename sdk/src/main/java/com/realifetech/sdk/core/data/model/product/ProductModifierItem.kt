package com.realifetech.sdk.core.data.model.product

import android.os.Parcelable
import com.realifetech.sdk.core.data.model.shared.translation.HasTranslation
import com.realifetech.sdk.core.data.model.shared.translation.StandardTranslation
import com.realifetech.type.ProductModifierItemStatus
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModifierItem(
    val id: String?,
    val status: ProductModifierItemStatus?,
    val externalId: String?,
    val additionalPrice: Int?,
    val position: Int?,
    override val translations: List<StandardTranslation?>?
) : Parcelable, HasTranslation<StandardTranslation?>