package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.api.Input
import com.realifetech.type.CardInput
import com.realifetech.type.PaymentSourceInput

data class PaymentSourceWrapper(
    val id: String? = null,
    val billingDetailsInput: PaymentSourceBillingDetailsWrapper? = null,
    val card: CardInput? = null
)

fun PaymentSourceWrapper.toInputObject() = PaymentSourceInput(
    Input.optional(id),
    Input.optional(billingDetailsInput?.toInputObject()),
    Input.optional(card)
)
