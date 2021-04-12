package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.api.Input
import com.realifetech.type.CardInput

data class CardWrapper(
    val brand: String,
    val numberToken: String,
    val expMonthToken: String,
    val expYearToken: String,
    val securityCode: String,
    val last4: String? = null
)

fun CardWrapper.toInputObject() =
    CardInput(brand, numberToken, expMonthToken, expYearToken, securityCode, Input.optional(last4))