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
        id: String,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) = dataSource.getMyPaymentIntent(id, callback)

    fun deleteMyPaymentSource(
        id: String,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) {
        dataSource.deleteMyPaymentSource(id, callback)
    }
}