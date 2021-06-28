package com.realifetech.core_sdk.data.shared.`object`

import android.os.Parcelable
import com.realifetech.core_sdk.data.shared.translation.Translation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeSlotTranslation(
    override val language: String?,
    val title: String?,
    val description: String?,
    val collectionNote: String?,
) : Parcelable, Translation