package com.realifetech.sdk.core.data.model.payment.wrapper

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceWrapper(
    val id: String?,
    val billingDetailsInput: PaymentSourceBillingDetailsWrapper?,
    val card: CardWrapper?
) : Parcelable

