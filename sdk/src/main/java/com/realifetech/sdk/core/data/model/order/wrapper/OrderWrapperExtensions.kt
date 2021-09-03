package com.realifetech.sdk.core.data.model.order.wrapper

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.realifetech.type.OrderUpdateInput

val OrderUpdateWrapper.asInput
    get() =
        OrderUpdateInput(
            Input.optional(status),
            Input.optional(collectionPreferenceType),
            Input.optional(checkInTime)
        ).toInput()