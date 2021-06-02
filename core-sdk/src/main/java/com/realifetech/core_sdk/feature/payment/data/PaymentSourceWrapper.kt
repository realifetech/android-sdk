package com.realifetech.core_sdk.feature.payment.data

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

fun PaymentSourceWrapper.toInputObject() = PaymentSourceInput(
    Input.optional(id),
    Input.optional(billingDetailsInput?.toInputObject()),
    Input.optional(card?.toInputObject())
)

fun List<PaymentSourceWrapper>.toInputList(): List<PaymentSourceInput> = map {
    PaymentSourceInput(
        Input.optional(it.id),
        Input.optional(it.billingDetailsInput?.toInputObject()),
        Input.optional(it.card?.toInputObject())
    )
}

