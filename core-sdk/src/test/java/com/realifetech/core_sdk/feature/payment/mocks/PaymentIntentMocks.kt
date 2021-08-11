package com.realifetech.core_sdk.feature.payment.mocks

import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentUpdateWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentWrapper
import com.realifetech.core_sdk.data.payment.wrapper.asInput
import com.realifetech.fragment.PaymentIntent
import com.realifetech.type.OrderType
import com.realifetech.type.PaymentStatus
import com.realifetech.type.UpdatePaymentStatus

object PaymentIntentMocks {

    val paymentIntent = PaymentIntent(
        "PaymentIntent",
        "",
        OrderType.FOOD_AND_BEVERAGE,
        "123",
        PaymentStatus.SUCCEEDED,
        null,
        123,
        "",
        false,
        null,
        false,
        null,
        null
    )
    val paymentIntentWrapper = PaymentIntentWrapper(
        OrderType.FOOD_AND_BEVERAGE,
        "123",
        null,
        123,
        "",
        false,
        false,
        null,
        null,
    )
    val paymentIntentInput = paymentIntentWrapper.asInput

    val paymentIntentUpdateWrapper = PaymentIntentUpdateWrapper(
        UpdatePaymentStatus.CANCELED,
        null,
        null
    )
    val paymentIntentUpdateInput = paymentIntentUpdateWrapper.asInput


}