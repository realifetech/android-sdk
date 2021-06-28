package com.realifetech.core_sdk.data.fulfilmentPoint

import android.os.Parcelable
import com.realifetech.core_sdk.data.shared.translation.HasTranslation
import com.realifetech.core_sdk.data.shared.`object`.TimeSlot
import com.realifetech.core_sdk.data.shared.`object`.WidgetItem
import com.realifetech.core_sdk.data.venue.Venue
import com.realifetech.type.FulfilmentPointType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FulfilmentPoint(
    val id: String,
    val externalId: String? = null,
    val reference: String? = null,
    val type: FulfilmentPointType? = null,
    val status: String? = null,
    val imageUrl: String? = null,
    val mapImageUrl: String? = null,
    val lat: String? = null,
    val long: String? = null,
    val waitTime: Int? = null,
    val prepTime: Int? = null,
    val position: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    override val translations: List<FulfilmentPointTranslation>? = null,
    val form: Form? = null,
    val categories: List<FulfilmentPointCategory>? = null,
    val venue: Venue? = null,
    val timeslots: List<TimeSlot>? = null
) : WidgetItem(), Parcelable, HasTranslation<FulfilmentPointTranslation>