package com.realifetech.core_sdk.data.payment.model

import android.os.Parcelable
import com.realifetech.core_sdk.data.payment.wrapper.CardWrapper
import com.realifetech.core_sdk.data.payment.wrapper.PaymentSourceBillingDetailsWrapper
import com.realifetech.type.PaymentSourceType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSource(
    val id: String,
    val type: PaymentSourceType,
    val default: Boolean,
    val billingDetails: PaymentSourceBillingDetailsWrapper?,
    val card: CardWrapper?
) : Parcelable