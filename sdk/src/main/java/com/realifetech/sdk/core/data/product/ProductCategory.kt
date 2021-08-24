package com.realifetech.sdk.core.data.product

import android.os.Parcelable
import com.realifetech.sdk.core.data.shared.translation.HasTranslation
import com.realifetech.sdk.core.data.shared.translation.StandardTranslation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductCategory(
    val id: String?,
    val externalId: String?,
    val reference: String?,
    val status: String?,
    val position: Int?,
    override val translations: List<StandardTranslation?>?
) : Parcelable, HasTranslation<StandardTranslation?>