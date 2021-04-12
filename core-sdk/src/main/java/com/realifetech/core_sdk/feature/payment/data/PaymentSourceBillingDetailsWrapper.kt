package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.api.toInput
import com.realifetech.type.PaymentSourceBillingDetailsInput

data class PaymentSourceBillingDetailsWrapper(
    val address: PaymentSourceAddressWrapper,
    val email: String,
    val name: String,
    val phone: String
)

fun PaymentSourceBillingDetailsWrapper.toInputObject() =
    PaymentSourceBillingDetailsInput(
        address.toInputObject().toInput(),
        email.toInput(),
        phone.toInput()
    )