package com.realifetech.core_sdk.feature.payment.data.paymentIntent

import android.os.Parcelable
import com.realifetech.fragment.PaymentIntent
import com.realifetech.type.PaymentActionType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NextActionParcelable(
    val type: PaymentActionType,
    val url: String?
) : Parcelable

fun PaymentIntent.NextAction.toParcelable() = NextActionParcelable(
    type, url
)