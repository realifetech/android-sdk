package com.realifetech.sdk.core.data.model.order.wrapper

import com.realifetech.type.CollectionPreferenceType
import com.realifetech.type.PaymentOrderStatus

data class OrderUpdateWrapper(
    val status: String?,
    val collectionPreferenceType: CollectionPreferenceType?,
    val checkInTime: String?,
    val paymentStatus: PaymentOrderStatus
)
