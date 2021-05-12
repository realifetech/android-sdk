package com.realifetech.core_sdk.feature.payment.data

import android.os.Parcelable
import com.apollographql.apollo.api.toInput
import com.realifetech.type.PaymentSourceBillingDetailsInput
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceBillingDetailsWrapper(
    val address: PaymentSourceAddressWrapper? = null,
    val email: String? = null,
    val name: String? = null,
    val phone: String? = null
) : Parcelable

fun PaymentSourceBillingDetailsWrapper.toInputObject() =
    PaymentSourceBillingDetailsInput(
        address?.toInputObject().toInput(),
        email.toInput(),
        phone.toInput()
    )