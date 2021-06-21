package com.realifetech.core_sdk.data.basket.model

import com.realifetech.core_sdk.data.shared.`object`.TimeSlot

data class Basket(
    val grossAmount: Int?,
    val discountAmount: Int?,
    val netAmount: Int?,
    val seatInfo: List<SeatInfo?>?,
    val timeslot: TimeSlot?,
    val collectionDate: String?,
    val collectionPreferenceType: CollectionPreferenceType?,
    val items: List<BasketItem>?
)