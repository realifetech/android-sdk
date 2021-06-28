package com.realifetech.core_sdk.data.shared.translation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StandardTranslation(
    val id: String?,
    override val language: String?,
    val title: String?
) : Parcelable, Translation