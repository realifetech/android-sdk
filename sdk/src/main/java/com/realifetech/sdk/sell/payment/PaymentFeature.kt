package com.realifetech.sdk.sell.payment

import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentUpdateWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentSourceWrapper
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentSource

class PaymentFeature private constructor() {
    private val paymentRepo = PaymentProvider().providePaymentRepository()

    fun addPaymentSource(
        input: PaymentSourceWrapper,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) {
        paymentRepo.addPaymentSource(input, callback)
    }

    fun getMyPaymentSources(
        pageSize: Int, page: Int?,
        callback: (error: Exception?, response: PaginatedObject<com.realifetech.core_sdk.data.payment.model.PaymentSource?>?) -> Unit
    ) {
        paymentRepo.getMyPaymentSources(pageSize, page, callback)
    }

    fun createPaymentIntent(
        input: PaymentIntentWrapper,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) {
        paymentRepo.createPaymentIntent(input, callback)
    }

    fun updatePaymentIntent(
        id: String,
        input: PaymentIntentUpdateWrapper,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) {
        paymentRepo.updatePaymentIntent(id, input, callback)
    }

    private object Holder {
        val instance = PaymentFeature()
    }

    companion object {
        val INSTANCE: PaymentFeature by lazy { Holder.instance }
    }
}