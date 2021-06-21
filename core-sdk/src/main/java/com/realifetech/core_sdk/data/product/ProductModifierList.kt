package com.realifetech.core_sdk.data.product

import android.os.Parcelable
import com.realifetech.core_sdk.data.shared.translation.StandardTranslation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModifierList(
    val id: String?,
    val status: String?,
    val externalId: String?,
    val reference: String?,
    val multipleSelect: Boolean?,
    val mandatorySelect: Boolean?,
    val maxAllowed: Int?,
    val items: List<ProductModifierItem?>?,
    val translations: List<StandardTranslation?>?
) : Parcelable