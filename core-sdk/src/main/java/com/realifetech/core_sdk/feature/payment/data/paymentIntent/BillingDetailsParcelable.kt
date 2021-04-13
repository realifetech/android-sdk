package com.realifetech.core_sdk.feature.payment.data.paymentIntent

import android.os.Parcelable
import com.realifetech.fragment.PaymentIntent
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BillingDetailsParcelable(
    val address: AddressParcelable?,
    val email: String?,
    val name: String,
    val phone: String?
) : Parcelable

fun PaymentIntent.BillingDetails.toParcelable() = BillingDetailsParcelable(
    address?.toParcelable(),
    email,
    name,
    phone
)

