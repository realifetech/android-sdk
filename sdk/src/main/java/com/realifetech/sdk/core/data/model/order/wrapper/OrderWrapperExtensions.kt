package com.realifetech.sdk.core.data.model.order.wrapper

import com.apollographql.apollo3.api.Optional
import com.realifetech.type.OrderUpdateInput

val OrderUpdateWrapper.asInput
    get() =
        Optional.Present(
            OrderUpdateInput(
                Optional.presentIfNotNull(status),
                Optional.presentIfNotNull(collectionPreferenceType),
                Optional.presentIfNotNull(checkInTime),
                Optional.presentIfNotNull(paymentStatus)
            )
        )