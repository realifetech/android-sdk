package com.realifetech.core_sdk.feature.fulfilmentpoint.domain

import com.realifetech.fragment.FragmentFulfilmentPoint
import com.realifetech.type.FulfilmentPointType

data class FulfilmentPoint(
    val id: String,
    val status: String? = null,
    val externalId: String? = null,
    val imageUrl: String? = null,
    val mapImageUrl: String? = null,
    val lat: Double? = null,
    val long: Double? = null,
    val type: FulfilmentPointType? = null,
    val position: Int? = null,
    val translations: List<FragmentFulfilmentPoint.Translation>? = null,
    val categories: List<FragmentFulfilmentPoint.Category>? = null,
    val timeslots: List<FragmentFulfilmentPoint.Timeslot>? = null,
    val seatForm: FragmentFulfilmentPoint.SeatForm?,
    val venue: FragmentFulfilmentPoint.Venue?,
    val prepTime: Int?,
    val waitTime: Int? = null,
    val reference: String?,
)