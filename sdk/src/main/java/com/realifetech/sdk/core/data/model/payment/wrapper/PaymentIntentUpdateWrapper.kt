package com.realifetech.sdk.core.data.model.payment.wrapper

import com.realifetech.type.UpdatePaymentStatus

data class PaymentIntentUpdateWrapper(
    val status: UpdatePaymentStatus?,
    val paymentSourceWrapper: PaymentSourceWrapper?,
    val savePaymentSource: Boolean?
)