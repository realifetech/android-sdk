package com.realifetech.core_sdk.feature.payment.data.paymentIntent

import android.os.Parcelable
import com.realifetech.fragment.PaymentIntent
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressParcelable(
    val city: String?,
    val country: String?,
    val line1: String?,
    val line2: String?,
    val postalCode: String?,
    val state: String?
) : Parcelable

fun PaymentIntent.Address.toParcelable() = AddressParcelable(
    city,
    country,
    line1,
    line2,
    postalCode,
    state
)