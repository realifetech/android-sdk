package com.realifetech.sdk.core.data.model.payment.wrapper

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardWrapper(
    val brand: String,
    val numberToken: String,
    val expMonthToken: String,
    val expYearToken: String,
    val securityCode: String,
    val last4: String
) : Parcelable