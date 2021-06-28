package com.realifetech.core_sdk.data.payment.model

import com.realifetech.core_sdk.data.payment.wrapper.asWrapper
import com.realifetech.fragment.PaymentSourceEdge

val PaymentSourceEdge.Edge.asModel: PaymentSource
    get() = PaymentSource(
        id = id,
        type = type,
        default = default_,
        billingDetails = billingDetails?.asWrapper,
        card = card?.asWrapper
    )