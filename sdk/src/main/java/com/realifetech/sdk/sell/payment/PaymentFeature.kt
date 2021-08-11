package com.realifetech.sdk.sell.payment

import com.realifetech.core_sdk.data.payment.model.PaymentSource
import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentUpdateWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentSourceWrapper
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.feature.payment.PaymentRepository
import com.realifetech.fragment.PaymentIntent

class PaymentFeature internal constructor(private val paymentRepo: PaymentRepository) {


    fun addPaymentSource(
        input: PaymentSourceWrapper,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) {
        paymentRepo.addPaymentSource(input, callback)
    }

    fun getMyPaymentSources(
        pageSize: Int, page: Int?,
        callback: (error: Exception?, response: PaginatedObject<PaymentSource?>?) -> Unit
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

    fun getMyPaymentIntent(
        id: String,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) {
        paymentRepo.getMyPaymentIntent(id, callback)
    }

    fun deletePaymentSource(
        id: String,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) {
        paymentRepo.deleteMyPaymentSource(id, callback)
    }


    private object Holder {
        val instance = PaymentFeature(PaymentProvider().providePaymentRepository())
    }

    companion object {
        val INSTANCE: PaymentFeature by lazy { Holder.instance }
    }
}