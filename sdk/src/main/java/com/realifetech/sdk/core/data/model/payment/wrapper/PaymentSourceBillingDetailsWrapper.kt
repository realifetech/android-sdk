package com.realifetech.sdk.core.data.model.payment.wrapper

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceBillingDetailsWrapper(
    val address: PaymentSourceAddressWrapper?,
    val email: String?,
    val name: String?,
    val phone: String?
) : Parcelable