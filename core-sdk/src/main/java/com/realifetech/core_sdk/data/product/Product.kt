package com.realifetech.core_sdk.data.product

import android.os.Parcelable
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.core_sdk.data.shared.translation.HasTranslation
import com.realifetech.core_sdk.data.shared.`object`.WidgetItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: String,
    val externalId: String?,
    val status: String?,
    val images: List<ProductImage?>?,
    override val translations: List<ProductTranslation?>?,
    val fulfilmentPoints: List<FulfilmentPoint?>?,
    val categories: List<ProductCategory?>?,
    val variants: List<ProductVariant?>?,
    val modifierLists: List<ProductModifierList?>?,
    val reference: String?
) : Parcelable, WidgetItem(), HasTranslation<ProductTranslation?>