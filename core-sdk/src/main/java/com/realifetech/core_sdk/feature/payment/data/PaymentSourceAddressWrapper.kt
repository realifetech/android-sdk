package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.api.toInput
import com.realifetech.type.PaymentSourceAddressInput

data class PaymentSourceAddressWrapper(
    val city: String,
    val country: String,
    val line1: String,
    val line2: String,
    val postalCode: String,
    val state: String
)

fun PaymentSourceAddressWrapper.toInputObject() = PaymentSourceAddressInput(
    city.toInput(),
    country.toInput(),
    line1.toInput(),
    line2.toInput(),
    postalCode.toInput(),
    state.toInput()
)