package com.realifetech.sdk.sell.payment

import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentSourceWrapper
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentSource
import com.realifetech.sdk.general.utils.executeCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaymentFeature private constructor() {
    private val paymentRepo = PaymentProvider().providePaymentRepository()

    fun addPaymentSource(
        input: PaymentSourceWrapper,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = paymentRepo.addPaymentSource(input)
            executeCallback(result, callback)
        }
    }

    fun getMyPaymentSources(
        pageSize: Int, page: Int?,
        callback: (error: Exception?, response: PaginatedObject<com.realifetech.core_sdk.data.payment.model.PaymentSource?>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = paymentRepo.getMyPaymentSources(pageSize, page)
            executeCallback(result, callback)
        }
    }

    fun createPaymentIntent(
        input: PaymentIntentWrapper,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = paymentRepo.createPaymentIntent(input)
            executeCallback(result, callback)
        }
    }

    fun updatePaymentIntent(
        id: String,
        input: PaymentIntentWrapper,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = paymentRepo.updatePaymentIntent(id, input)
            executeCallback(result, callback)
        }
    }

    private object Holder {
        val instance = PaymentFeature()
    }

    companion object {
        val INSTANCE: PaymentFeature by lazy { Holder.instance }
    }
}