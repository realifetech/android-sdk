package com.realifetech.core_sdk.feature.payment.data.paymentIntent

import android.os.Parcelable
import com.realifetech.fragment.PaymentIntent
import com.realifetech.type.OrderType
import com.realifetech.type.PaymentStatus
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentIntentParcelable(
    val id: String,
    val orderType: OrderType,
    val orderId: String,
    val status: PaymentStatus,
    val paymentSource: PaymentSourceParcelable?,
    val amount: Int,
    val currency: String,
    val liveMode: Boolean,
    val cancellationReason: String?,
    val savePaymentSource: Boolean?,
    val receiptEmail: String?,
    val nextAction: NextActionParcelable?
) : Parcelable


fun PaymentIntent.toPaymentIntentSerializable() =
    PaymentIntentParcelable(
        id,
        orderType,
        orderId,
        status,
        paymentSource?.toParcelable(),
        amount,
        currency,
        livemode,
        cancellationReason,
        savePaymentSource,
        receiptEmail,
        nextAction?.toParcelable()
    )