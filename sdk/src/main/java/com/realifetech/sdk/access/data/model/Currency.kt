package com.realifetech.sdk.access.data.model

import android.os.Parcelable
import com.realifetech.fragment.FragmentTicket
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    val id: Int?,
    val title: String?,
    val isoCode: String?,
    val sign: String?
) : Parcelable

fun Currency.fromFragmentTicket(response: FragmentTicket.Currency) = Currency(
    id = response.id,
    title = response.title,
    isoCode = response.isoCode,
    sign = response.sign
)
