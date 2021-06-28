package com.realifetech.core_sdk.data.basket.model

import com.realifetech.core_sdk.data.shared.`object`.TimeSlot
import com.realifetech.type.CollectionPreferenceType

data class Basket(
    val grossAmount: Int?,
    val discountAmount: Int?,
    val netAmount: Int?,
    val seatInfo: SeatInfo?,
    val timeslot: TimeSlot?,
    val collectionDate: String?,
    val collectionPreferenceType: CollectionPreferenceType?,
    val items: List<BasketItem>?
)