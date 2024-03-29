package com.realifetech.sdk.core.data.model.shared.`object`

import android.os.Parcelable
import com.realifetech.sdk.core.data.model.shared.translation.Translation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeSlotTranslation(
    override val language: String?,
    val title: String?,
    val description: String?,
    val collectionNote: String?,
) : Parcelable, Translation