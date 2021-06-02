package com.realifetech.core_sdk.feature.payment.data

import android.os.Parcelable
import com.apollographql.apollo.api.Input
import com.realifetech.type.PaymentCustomerInput
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentCustomerWrapper(
    val id: String? = null,
    val user: String? = null,
    val externalId: String? = null,
    val paymentSources: List<PaymentSourceWrapper>
) : Parcelable

fun PaymentCustomerWrapper.toInputObject() = PaymentCustomerInput(
    Input.optional(id),
    Input.optional(user),
    Input.optional(externalId),
    Input.optional(paymentSources.toInputList())
)
