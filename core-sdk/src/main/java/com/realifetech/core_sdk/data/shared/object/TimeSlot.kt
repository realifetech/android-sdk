package com.realifetech.core_sdk.data.shared.`object`

import android.os.Parcelable
import com.realifetech.core_sdk.data.shared.translation.HasTranslation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimeSlot(
    val id: String,
    val externalId: String?,
    val reference: String?,
    val startTime: String?,
    val endTime: String?,
    val createdAt: String?,
    val updatedAt: String?,
    override val translations: List<TimeSlotTranslation?>?
) : Parcelable, HasTranslation<TimeSlotTranslation?>