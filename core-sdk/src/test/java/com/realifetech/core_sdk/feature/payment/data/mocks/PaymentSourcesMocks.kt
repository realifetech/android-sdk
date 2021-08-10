package com.realifetech.core_sdk.feature.payment.data.mocks

import com.realifetech.core_sdk.data.payment.wrapper.PaymentSourceWrapper
import com.realifetech.core_sdk.data.payment.wrapper.asInput
import com.realifetech.fragment.FragmentPaymentSource
import com.realifetech.fragment.PaymentSourceEdge.Edge
import com.realifetech.type.PaymentSourceType

object PaymentSourcesMocks {
    val paymentSource = FragmentPaymentSource("", "123", PaymentSourceType.CARD, true, null, null)
    val paymentSourceInput = PaymentSourceWrapper("123", null, null).asInput
    val paymentSources = listOf(
        Edge("", "21", PaymentSourceType.CARD, true, null, null),
        Edge("", "21", PaymentSourceType.CARD, true, null, null)
    )
    val emptyPaymentSources = listOf<Edge?>(null, null)
}