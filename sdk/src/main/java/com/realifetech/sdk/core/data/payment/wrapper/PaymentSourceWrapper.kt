package com.realifetech.sdk.core.data.payment.wrapper

import android.os.Parcelable
import com.apollographql.apollo.api.Input
import com.realifetech.type.PaymentSourceInput
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceWrapper(
    val id: String?,
    val billingDetailsInput: PaymentSourceBillingDetailsWrapper?,
    val card: CardWrapper?
) : Parcelable

