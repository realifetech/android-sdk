package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.api.Input
import com.realifetech.type.CardInput
import com.realifetech.type.PaymentSourceInput

data class PaymentSourceWrapper(
    val id: String,
    val billingDetailsInput: PaymentSourceBillingDetailsWrapper,
    val card: CardInput
)

fun PaymentSourceWrapper.toInputObject() = PaymentSourceInput(
    Input.optional(id),
    Input.optional(billingDetailsInput.toInputObject()),
    Input.optional(card)
)
