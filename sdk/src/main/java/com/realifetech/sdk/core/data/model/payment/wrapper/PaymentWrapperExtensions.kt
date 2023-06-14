package com.realifetech.sdk.core.data.model.payment.wrapper

import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.toInput
import com.realifetech.fragment.PaymentSourceEdge
import com.realifetech.type.*

val PaymentSourceEdge.BillingDetails.asWrapper: PaymentSourceBillingDetailsWrapper
    get() = PaymentSourceBillingDetailsWrapper(
        address = address?.asWrapper,
        email = email,
        name = name,
        phone = phone
    )

val PaymentSourceEdge.Address.asWrapper: PaymentSourceAddressWrapper
    get() = PaymentSourceAddressWrapper(
        city = city,
        country = country,
        line1 = line1,
        line2 = line2,
        postalCode = postalCode,
        state = state
    )

val PaymentSourceEdge.Card.asWrapper: CardWrapper
    get() = CardWrapper(
        brand = brand,
        numberToken = numberToken.orEmpty(),
        expMonthToken = expMonth.orEmpty(),
        expYearToken = expYear.orEmpty(),
        securityCode = fingerprint.orEmpty(),
        last4 = last4
    )

val CardWrapper.asInput
    get() =
        CardInput(brand, numberToken, expMonthToken, expYearToken, securityCode, last4)

val PaymentIntentWrapper.asInput
    get() = PaymentIntentInput(
        orderType,
        orderId,
        Optional.presentIfNotNull(paymentSource?.asInput),
        amount,
        currency,
        savePaymentSource,
        livemode,
        Optional.presentIfNotNull(cancellationReason),
        Optional.presentIfNotNull(receiptEmail)
    )

val PaymentIntentUpdateWrapper.asInput
    get() = PaymentIntentUpdateInput(
        Optional.presentIfNotNull(status),
        Optional.presentIfNotNull(paymentSourceWrapper?.asInput),
        Optional.presentIfNotNull(savePaymentSource)
    )

val PaymentSourceAddressWrapper.asInput
    get() = PaymentSourceAddressInput(
        Optional.presentIfNotNull(city),
        Optional.presentIfNotNull(country),
        Optional.presentIfNotNull(line1),
        Optional.presentIfNotNull(line2),
        Optional.presentIfNotNull(postalCode),
        Optional.presentIfNotNull(state)
    )

val PaymentSourceBillingDetailsWrapper.asInput
    get() =
        PaymentSourceBillingDetailsInput(
            address?.asInput.toInput(),
            Optional.presentIfNotNull(email),
            Optional.presentIfNotNull(name),
            Optional.presentIfNotNull(phone)
        )

val PaymentSourceWrapper.asInput
    get() = PaymentSourceInput(
        Optional.presentIfNotNull(id),
        Optional.Present(TOKEN_PROVIDER),
        Optional.Present(PaymentSourceType.card),
        Optional.presentIfNotNull(billingDetailsInput?.asInput),
        Optional.presentIfNotNull(card?.asInput)
    )

fun convertPaymentSourceWrapperToInput(items: List<PaymentSourceWrapper>): List<PaymentSourceInput> =
    items.map {
        PaymentSourceInput(
            Optional.presentIfNotNull(it.id),
            Optional.Present(TOKEN_PROVIDER),
            Optional.Present(PaymentSourceType.card),
            Optional.presentIfNotNull(it.billingDetailsInput?.asInput),
            Optional.presentIfNotNull(it.card?.asInput)
        )
    }


const val TOKEN_PROVIDER = "vgs"