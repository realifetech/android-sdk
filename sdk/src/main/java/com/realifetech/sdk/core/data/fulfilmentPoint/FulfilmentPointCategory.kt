package com.realifetech.sdk.core.data.fulfilmentPoint

import android.os.Parcelable
import com.realifetech.sdk.core.data.shared.translation.HasTranslation
import com.realifetech.sdk.core.data.shared.translation.StandardTranslation
import com.realifetech.sdk.core.data.shared.`object`.WidgetItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FulfilmentPointCategory(
    val id: String,
    val reference: String?,
    val status: String?,
    val iconImageUrl: String?,
    val position: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    override val translations: List<StandardTranslation>?
) : Parcelable, HasTranslation<StandardTranslation>, WidgetItem()