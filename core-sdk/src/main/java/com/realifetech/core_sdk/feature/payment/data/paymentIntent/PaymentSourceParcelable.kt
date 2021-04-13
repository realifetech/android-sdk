package com.realifetech.core_sdk.feature.payment.data.paymentIntent

import android.os.Parcelable
import com.realifetech.fragment.PaymentIntent.*
import com.realifetech.type.PaymentSourceType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceParcelable(
    val id: String,
    val type: PaymentSourceType,
    val default: Boolean,
    val billingDetails: BillingDetailsParcelable?,
    val card: CardParcelable?
) : Parcelable

fun PaymentSource.toParcelable() = PaymentSourceParcelable(
    id, type, default_, billingDetails?.toParcelable(), card?.toParcelable()
)