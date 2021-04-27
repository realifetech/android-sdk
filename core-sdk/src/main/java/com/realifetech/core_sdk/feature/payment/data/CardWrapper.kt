package com.realifetech.core_sdk.feature.payment.data

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

fun CardWrapper.toInputObject() =
    CardInput(brand, numberToken, expMonthToken, expYearToken, securityCode, last4)