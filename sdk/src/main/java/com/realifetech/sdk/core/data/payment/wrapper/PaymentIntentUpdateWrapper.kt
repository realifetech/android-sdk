package com.realifetech.sdk.core.data.payment.wrapper

import com.realifetech.type.UpdatePaymentStatus

data class PaymentIntentUpdateWrapper(
    val status: UpdatePaymentStatus?,
    val paymentSourceWrapper: PaymentSourceWrapper?,
    val savePaymentSource: Boolean?
)