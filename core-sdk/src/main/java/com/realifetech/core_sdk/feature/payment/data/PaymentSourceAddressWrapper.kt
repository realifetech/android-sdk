package com.realifetech.core_sdk.feature.payment.data

import android.os.Parcelable
import com.apollographql.apollo.api.toInput
import com.realifetech.type.PaymentSourceAddressInput
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceAddressWrapper(
    val city: String? = null,
    val country: String? = null,
    val line1: String? = null,
    val line2: String? = null,
    val postalCode: String? = null,
    val state: String? = null
):Parcelable

fun PaymentSourceAddressWrapper.toInputObject() = PaymentSourceAddressInput(
    city.toInput(),
    country.toInput(),
    line1.toInput(),
    line2.toInput(),
    postalCode.toInput(),
    state.toInput()
)