package com.realifetech.core_sdk.feature.payment

import com.realifetech.core_sdk.data.payment.model.PaymentSource
import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentUpdateWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentIntentWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentSourceWrapper
import com.realifetech.core_sdk.data.payment.wrapper.asInput
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.domain.Result
import com.realifetech.fragment.PaymentIntent
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentIntentUpdateInput
import com.realifetech.type.PaymentSourceInput

class PaymentRepository(private val dataSource: DataSource) {

    fun addPaymentSource(
        input: PaymentSourceWrapper,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) =
        dataSource.addPaymentSource(input.asInput, callback)

    fun getMyPaymentSources(
        pageSize: Int, page: Int?,
        callback: (error: Exception?, response: PaginatedObject<PaymentSource?>?) -> Unit
    ) =
        dataSource.getMyPaymentSources(pageSize, page, callback)

    fun createPaymentIntent(
        input: PaymentIntentWrapper,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) =
        dataSource.createPaymentIntent(input.asInput, callback)

    fun updatePaymentIntent(
        id: String,
        input: PaymentIntentUpdateWrapper,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) =
        dataSource.updatePaymentIntent(id, input.asInput, callback)

    fun getMyPaymentIntent(
        id: String
    ): Result<PaymentIntent>? = dataSource.getMyPaymentIntent(id)

    fun deleteMyPaymentSource(
        id: String,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) {
        dataSource.deleteMyPaymentSource(id, callback)
    }


    interface DataSource {
        fun addPaymentSource(
            input: PaymentSourceInput,
            callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
        )

        fun getMyPaymentSources(
            pageSize: Int,
            page: Int?,
            callback: (error: Exception?, response: PaginatedObject<PaymentSource?>?) -> Unit
        )

        fun createPaymentIntent(
            input: PaymentIntentInput,
            callback: (error: Exception?, response: PaymentIntent?) -> Unit
        )

        fun updatePaymentIntent(
            id: String,
            input: PaymentIntentUpdateInput,
            callback: (error: Exception?, response: PaymentIntent?) -> Unit
        )

        fun getMyPaymentIntent(
            id: String
        ): Result<PaymentIntent>?

        fun deleteMyPaymentSource(
            id: String,
            callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
        )
    }
}