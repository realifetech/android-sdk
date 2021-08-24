package com.realifetech.sdk.core.data.product

import android.os.Parcelable
import com.realifetech.sdk.core.data.shared.translation.Translation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductTranslation(
    val id: String?,
    override val language: String?,
    val title: String?,
    val description: String?
) : Parcelable, Translation