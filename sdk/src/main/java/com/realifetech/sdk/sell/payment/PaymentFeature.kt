package com.realifetech.sdk.sell.payment

import com.realifetech.fragment.PaymentIntent
import com.realifetech.sdk.core.data.model.payment.model.PaymentSource
import com.realifetech.sdk.core.data.model.payment.wrapper.PaymentIntentUpdateWrapper
import com.realifetech.sdk.core.data.model.payment.wrapper.PaymentIntentWrapper
import com.realifetech.sdk.core.data.model.payment.wrapper.PaymentSourceWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.payment.domain.PaymentRepository
import javax.inject.Inject

class PaymentFeature @Inject constructor(private val paymentRepo: PaymentRepository) {

    suspend fun addPaymentSource(input: PaymentSourceWrapper): PaymentSource? {
        return paymentRepo.addPaymentSource(input)
    }

    suspend fun getMyPaymentSources(pageSize: Int, page: Int?): PaginatedObject<PaymentSource?>? {
        return paymentRepo.getMyPaymentSources(pageSize, page)
    }

    suspend fun createPaymentIntent(input: PaymentIntentWrapper): PaymentIntent? {
        return paymentRepo.createPaymentIntent(input)
    }

    suspend fun updatePaymentIntent(id: String, input: PaymentIntentUpdateWrapper): PaymentIntent? {
        return paymentRepo.updatePaymentIntent(id, input)
    }

    suspend fun getMyPaymentIntent(id: String): PaymentIntent? {
        return paymentRepo.getMyPaymentIntent(id)
    }

    suspend fun deletePaymentSource(id: String): PaymentSource? {
        return paymentRepo.deleteMyPaymentSource(id)
    }
}