package com.realifetech.sdk.core.data.model.payment.wrapper

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
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
        Input.optional(paymentSource?.asInput),
        amount,
        currency,
        savePaymentSource,
        livemode,
        Input.optional(cancellationReason),
        Input.optional(receiptEmail)
    )

val PaymentIntentUpdateWrapper.asInput
    get() = PaymentIntentUpdateInput(
        Input.optional(status),
        Input.optional(paymentSourceWrapper?.asInput),
        Input.optional(savePaymentSource)
    )

val PaymentSourceAddressWrapper.asInput
    get() = PaymentSourceAddressInput(
        city.toInput(),
        country.toInput(),
        line1.toInput(),
        line2.toInput(),
        postalCode.toInput(),
        state.toInput()
    )

val PaymentSourceBillingDetailsWrapper.asInput
    get() =
        PaymentSourceBillingDetailsInput(
            address?.asInput.toInput(),
            email.toInput(),
            name.toInput(),
            phone.toInput()
        )

val PaymentSourceWrapper.asInput
    get() = PaymentSourceInput(
        Input.optional(id),
        TOKEN_PROVIDER.toInput(),
        PaymentSourceType.CARD.toInput(),
        Input.optional(billingDetailsInput?.asInput),
        Input.optional(card?.asInput)
    )

fun convertPaymentSourceWrapperToInput(items: List<PaymentSourceWrapper>): List<PaymentSourceInput> =
    items.map {
        PaymentSourceInput(
            Input.optional(it.id),
            TOKEN_PROVIDER.toInput(),
            PaymentSourceType.CARD.toInput(),
            Input.optional(it.billingDetailsInput?.asInput),
            Input.optional(it.card?.asInput)
        )
    }


const val TOKEN_PROVIDER = "vgs"