package com.realifetech.core_sdk.data.basket.model

import com.realifetech.type.CollectionPreferenceType

data class BasketRequest(
    val timeslotId: String?,
    val collectionDate: String?,
    val items: MutableList<BasketRequestItem>,
    val collectionPreferenceType: CollectionPreferenceType?,
    val fulfilmentPoint: String?,
    val seatInfo: SeatInfo?
)