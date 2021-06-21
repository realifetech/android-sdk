package com.realifetech.core_sdk.data.payment.wrapper

import android.os.Parcelable
import com.apollographql.apollo.api.Input
import com.realifetech.type.PaymentSourceInput
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceWrapper(
    val id: String? = null,
    val billingDetailsInput: PaymentSourceBillingDetailsWrapper? = null,
    val card: CardWrapper? = null
) : Parcelable

