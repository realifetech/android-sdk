package com.realifetech.core_sdk.data.order.wrapper

import com.realifetech.type.CollectionPreferenceType

data class OrderUpdateWrapper(
    val status: String?,
    val collectionPreferenceType: CollectionPreferenceType?,
    val checkInTime: String?
)
