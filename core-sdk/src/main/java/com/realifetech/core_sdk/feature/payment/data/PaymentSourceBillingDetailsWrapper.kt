package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.api.toInput
import com.realifetech.type.PaymentSourceBillingDetailsInput

data class PaymentSourceBillingDetailsWrapper(
    val address: PaymentSourceAddressWrapper? = null,
    val email: String? = null,
    val name: String? = null,
    val phone: String? = null
)

fun PaymentSourceBillingDetailsWrapper.toInputObject() =
    PaymentSourceBillingDetailsInput(
        address?.toInputObject().toInput(),
        email.toInput(),
        phone.toInput()
    )