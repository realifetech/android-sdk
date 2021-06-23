package com.realifetech.core_sdk.feature.payment

import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentUpdateWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentSourceWrapper
import com.realifetech.core_sdk.data.payment.wrapper.asInput
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.domain.Result
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentSource
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentIntentUpdateInput
import com.realifetech.type.PaymentSourceInput

class PaymentRepository(private val dataSource: DataSource) {

    suspend fun addPaymentSource(input: PaymentSourceWrapper) =
        dataSource.addPaymentSource(input.asInput)

    suspend fun getMyPaymentSources(pageSize: Int, page: Int?) =
        dataSource.getMyPaymentSources(pageSize, page)

    suspend fun createPaymentIntent(input: PaymentIntentWrapper) =
        dataSource.createPaymentIntent(input.asInput)

    suspend fun updatePaymentIntent(id: String, input: PaymentIntentUpdateWrapper) =
        dataSource.updatePaymentIntent(id, input.asInput)


    interface DataSource {
        suspend fun addPaymentSource(input: PaymentSourceInput): Result<PaymentSource>
        suspend fun getMyPaymentSources(
            pageSize: Int,
            page: Int?
        ): Result<PaginatedObject<com.realifetech.core_sdk.data.payment.model.PaymentSource?>>

        suspend fun createPaymentIntent(input: PaymentIntentInput): Result<PaymentIntent>
        suspend fun updatePaymentIntent(
            id: String,
            input: PaymentIntentUpdateInput
        ): Result<PaymentIntent>
    }
}