package com.realifetech.sdk.core.data.payment.model

import android.os.Parcelable
import com.realifetech.sdk.core.data.payment.wrapper.CardWrapper
import com.realifetech.sdk.core.data.payment.wrapper.PaymentSourceBillingDetailsWrapper
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