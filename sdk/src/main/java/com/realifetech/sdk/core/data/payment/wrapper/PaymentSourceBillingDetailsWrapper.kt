package com.realifetech.sdk.core.data.payment.wrapper

import android.os.Parcelable
import com.apollographql.apollo.api.toInput
import com.realifetech.type.PaymentSourceBillingDetailsInput
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceBillingDetailsWrapper(
    val address: PaymentSourceAddressWrapper?,
    val email: String?,
    val name: String?,
    val phone: String?
) : Parcelable