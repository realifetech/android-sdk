package com.realifetech.core_sdk.feature.payment.data.mocks

import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentWrapper
import com.realifetech.core_sdk.data.payment.wrapper.asInput
import com.realifetech.fragment.PaymentIntent
import com.realifetech.type.OrderType
import com.realifetech.type.PaymentStatus

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
     val paymentIntentInput = PaymentIntentWrapper(
        OrderType.FOOD_AND_BEVERAGE,
        "123",
        null,
        123,
        "",
        false,
        false,
        null,
        null,
    ).asInput



}