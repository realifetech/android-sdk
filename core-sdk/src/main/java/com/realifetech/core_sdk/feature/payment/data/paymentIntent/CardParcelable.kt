package com.realifetech.core_sdk.feature.payment.data.paymentIntent

import android.os.Parcelable
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentIntent.*
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardParcelable(
    val brand: String,
    val numberToken: String,
    val expMonth: String,
    val expYear: String,
    val last4: String,
    val fingerprint: String?
) : Parcelable

fun Card.toParcelable() = CardParcelable(
    brand,
    numberToken,
    expMonth,
    expYear,
    last4,
    fingerprint,
)