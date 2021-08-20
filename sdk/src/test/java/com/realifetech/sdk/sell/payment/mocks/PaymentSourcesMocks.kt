package com.realifetech.sdk.sell.payment.mocks

import com.realifetech.core_sdk.data.payment.wrapper.PaymentSourceWrapper
import com.realifetech.core_sdk.data.payment.wrapper.asInput
import com.realifetech.fragment.FragmentPaymentSource
import com.realifetech.fragment.PaymentSourceEdge.Edge
import com.realifetech.type.PaymentSourceType

object PaymentSourcesMocks {
    val paymentSource = FragmentPaymentSource("", "123", PaymentSourceType.CARD, true, null, null)
    val paymentSourceWrapper = PaymentSourceWrapper("123", null, null)
    val paymentSources = listOf(
        Edge("", "21", PaymentSourceType.CARD, true, null, null),
        Edge("", "21", PaymentSourceType.CARD, true, null, null)
    )
}