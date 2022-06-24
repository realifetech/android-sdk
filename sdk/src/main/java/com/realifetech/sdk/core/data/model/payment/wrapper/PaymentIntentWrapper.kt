package com.realifetech.sdk.core.data.model.payment.wrapper

import android.os.Parcelable
import com.realifetech.type.CancellationReason
import com.realifetech.type.OrderType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentIntentWrapper(
    val orderType: OrderType,
    val orderId: String,
    val paymentSource: PaymentSourceWrapper?,
    val amount: Int,
    val currency: String,
    val savePaymentSource: Boolean,
    val livemode: Boolean,
    val cancellationReason: CancellationReason?,
    val receiptEmail: String?
) : Parcelable