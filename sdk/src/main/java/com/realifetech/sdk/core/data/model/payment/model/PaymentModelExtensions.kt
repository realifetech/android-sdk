package com.realifetech.sdk.core.data.model.payment.model

import com.realifetech.sdk.core.data.model.payment.wrapper.CardWrapper
import com.realifetech.sdk.core.data.model.payment.wrapper.PaymentSourceAddressWrapper
import com.realifetech.sdk.core.data.model.payment.wrapper.PaymentSourceBillingDetailsWrapper
import com.realifetech.sdk.core.data.model.payment.wrapper.asWrapper
import com.realifetech.fragment.FragmentPaymentSource
import com.realifetech.fragment.PaymentSourceEdge

val PaymentSourceEdge.Edge.asModel: PaymentSource
    get() = PaymentSource(
        id = id,
        type = type,
        default = default_,
        billingDetails = billingDetails?.asWrapper,
        card = card?.asWrapper
    )

val FragmentPaymentSource.asModel: PaymentSource
    get() = PaymentSource(
        id = id,
        type = type,
        default = default_,
        billingDetails = billingDetails?.asModel,
        card = card?.asModel
    )

val FragmentPaymentSource.BillingDetails.asModel: PaymentSourceBillingDetailsWrapper
    get() = PaymentSourceBillingDetailsWrapper(
        address = address?.asModel,
        email = email, name = name, phone = phone
    )

val FragmentPaymentSource.Address.asModel: PaymentSourceAddressWrapper
    get() = PaymentSourceAddressWrapper(
        city = city,
        country = country,
        line1 = line1,
        line2 = line2,
        postalCode = postalCode,
        state = state
    )

val FragmentPaymentSource.Card.asModel: CardWrapper
    get() = CardWrapper(
        brand = brand,
        numberToken = numberToken.orEmpty(),
        expMonthToken = expMonth.orEmpty(),
        expYearToken = expYear.orEmpty(),
        securityCode = fingerprint.orEmpty(),
        last4 = last4
    )
