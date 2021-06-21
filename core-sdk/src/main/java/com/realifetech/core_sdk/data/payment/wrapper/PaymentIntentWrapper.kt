package com.realifetech.core_sdk.data.payment.wrapper

import android.os.Parcelable
import com.apollographql.apollo.api.Input
import com.realifetech.type.CancellationReason
import com.realifetech.type.OrderType
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentStatus
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentIntentWrapper(
    val orderType: OrderType,
    val orderId: String,
    val paymentSource: PaymentSourceWrapper? = null,
    val paymentCustomerWrapper: PaymentCustomerWrapper,
    val amount: Int,
    val currency: String,
    val savePaymentSource: Boolean,
    val livemode: Boolean,
    val cancellationReason: CancellationReason? = null,
    val receiptEmail: String? = null
) : Parcelable