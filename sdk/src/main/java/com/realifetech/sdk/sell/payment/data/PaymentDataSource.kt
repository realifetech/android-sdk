package com.realifetech.sdk.sell.payment.data

import com.realifetech.fragment.PaymentIntent
import com.realifetech.sdk.core.data.model.payment.model.PaymentSource
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentIntentUpdateInput
import com.realifetech.type.PaymentSourceInput

interface PaymentDataSource {
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
        id: String,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    )

    fun deleteMyPaymentSource(
        id: String,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    )
}