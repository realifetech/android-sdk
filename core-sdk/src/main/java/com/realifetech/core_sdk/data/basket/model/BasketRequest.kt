package com.realifetech.core_sdk.data.basket.model

data class BasketRequest(
    val timeslotId: String?,
    val collectionDate: String?,
    val items: MutableList<BasketRequestItem>,
    val collectionPreferenceType: String?,
    val fulfilmentPoint: String?,
    val seatInfo: List<SeatInfo?>?
)