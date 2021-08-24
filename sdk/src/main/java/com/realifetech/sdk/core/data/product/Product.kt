package com.realifetech.sdk.core.data.product

import android.os.Parcelable
import com.realifetech.sdk.core.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.shared.`object`.WidgetItem
import com.realifetech.sdk.core.data.shared.translation.HasTranslation
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