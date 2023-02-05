package com.realifetech.sdk.access.data.model

import android.os.Parcelable
import com.realifetech.fragment.FragmentTicket
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdditionalField(
    val name: String?,
    val value: String?
) : Parcelable

fun AdditionalField.fromFragmentTicket(response: FragmentTicket.AdditionalField) = AdditionalField(
    name = response.name,
    value = response.value
)