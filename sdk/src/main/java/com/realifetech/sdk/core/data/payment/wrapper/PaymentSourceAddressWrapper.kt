package com.realifetech.sdk.core.data.payment.wrapper

import android.os.Parcelable
import com.apollographql.apollo.api.toInput
import com.realifetech.type.PaymentSourceAddressInput
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceAddressWrapper(
    val city: String?,
    val country: String?,
    val line1: String?,
    val line2: String?,
    val postalCode: String?,
    val state: String?
):Parcelable