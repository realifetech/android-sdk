package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.api.Input
import com.realifetech.type.OrderType
import com.realifetech.type.PaymentIntentInput

data class PaymentIntentWrapper(
    val orderType: OrderType,
    val orderId: String,
    val paymentSource: PaymentSourceWrapper,
    val amount: Int,
    val currency: String,
    val savePaymentSource: Boolean,
    val livemode: Boolean,
    val cancellationReason: String,
    val receiptEmail: String
)

fun PaymentIntentWrapper.toInputObject() = PaymentIntentInput(
    orderType,
    orderId,
    Input.optional(paymentSource.toInputObject()),
    amount,
    currency,
    savePaymentSource,
    livemode,
    Input.optional(cancellationReason),
    Input.optional(receiptEmail)
)