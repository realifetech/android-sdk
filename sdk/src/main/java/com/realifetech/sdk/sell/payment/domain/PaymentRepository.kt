package com.realifetech.sdk.sell.payment.domain

import com.realifetech.fragment.PaymentIntent
import com.realifetech.sdk.core.data.model.payment.model.PaymentSource
import com.realifetech.sdk.core.data.model.payment.wrapper.PaymentIntentUpdateWrapper
import com.realifetech.sdk.core.data.model.payment.wrapper.PaymentIntentWrapper
import com.realifetech.sdk.core.data.model.payment.wrapper.PaymentSourceWrapper
import com.realifetech.sdk.core.data.model.payment.wrapper.asInput
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.payment.data.PaymentDataSource

class PaymentRepository(private val dataSource: PaymentDataSource) {

    suspend fun addPaymentSource(input: PaymentSourceWrapper): PaymentSource? {
        return dataSource.addPaymentSource(input.asInput)
    }

    suspend fun getMyPaymentSources(pageSize: Int, page: Int?): PaginatedObject<PaymentSource?>? {
        return dataSource.getMyPaymentSources(pageSize, page)
    }

    suspend fun createPaymentIntent(input: PaymentIntentWrapper): PaymentIntent? {
        return dataSource.createPaymentIntent(input.asInput)
    }

    suspend fun updatePaymentIntent(id: String, input: PaymentIntentUpdateWrapper): PaymentIntent? {
        return dataSource.updatePaymentIntent(id, input.asInput)
    }

    suspend fun getMyPaymentIntent(id: String): PaymentIntent? {
        return dataSource.getMyPaymentIntent(id)
    }

    suspend fun deleteMyPaymentSource(id: String): PaymentSource? {
        return dataSource.deleteMyPaymentSource(id)
    }
}