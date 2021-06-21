package com.realifetech.core_sdk.data.payment.wrapper

import android.os.Parcelable
import com.realifetech.type.CardInput
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