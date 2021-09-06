package com.realifetech.sdk.core.data.model.basket

import com.realifetech.type.CollectionPreferenceType

data class BasketRequest(
    val timeslotId: String?,
    val collectionDate: String?,
    val items: MutableList<BasketRequestItem>,
    val collectionPreferenceType: CollectionPreferenceType?,
    val fulfilmentPoint: String?,
    val seatInfo: Map<String, String>?
)