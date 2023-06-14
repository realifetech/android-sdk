package com.realifetech.sdk.sell.payment.data

import com.realifetech.fragment.PaymentIntent
import com.realifetech.sdk.core.data.model.payment.model.PaymentSource
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentIntentUpdateInput
import com.realifetech.type.PaymentSourceInput

interface PaymentDataSource {
    suspend fun addPaymentSource(input: PaymentSourceInput): PaymentSource?

    suspend fun getMyPaymentSources(
        pageSize: Int,
        page: Int?
    ): PaginatedObject<PaymentSource?>?

    suspend fun createPaymentIntent(input: PaymentIntentInput): PaymentIntent?

    suspend fun updatePaymentIntent(
        id: String,
        input: PaymentIntentUpdateInput
    ): PaymentIntent?

    suspend fun getMyPaymentIntent(id: String): PaymentIntent?

    suspend fun deleteMyPaymentSource(id: String): PaymentSource?
}