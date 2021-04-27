package com.realifetech.core_sdk.feature.payment.data

import android.os.Parcelable
import com.apollographql.apollo.api.Input
import com.realifetech.fragment.PaymentSource
import com.realifetech.type.PaymentSourceInput
import com.realifetech.type.PaymentSourceType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentSourceWrapper(
    val id: String? = null,
    val billingDetailsInput: PaymentSourceBillingDetailsWrapper? = null,
    val card: CardWrapper? = null
) : Parcelable

fun PaymentSourceWrapper.toInputObject() = PaymentSourceInput(
    Input.optional(id),
    Input.optional(billingDetailsInput?.toInputObject()),
    Input.optional(card?.toInputObject())
)
