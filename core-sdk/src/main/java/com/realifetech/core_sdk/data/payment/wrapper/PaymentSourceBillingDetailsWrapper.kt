package com.realifetech.core_sdk.data.payment.wrapper

import android.os.Parcelable
import com.apollographql.apollo.api.toInput
import com.realifetech.type.PaymentSourceBillingDetailsInput
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceBillingDetailsWrapper(
    val address: PaymentSourceAddressWrapper? = null,
    val email: String? = null,
    val name: String? = null,
    val phone: String? = null
) : Parcelable