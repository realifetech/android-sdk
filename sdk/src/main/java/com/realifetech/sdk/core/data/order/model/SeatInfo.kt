package com.realifetech.sdk.core.data.order.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeatInfo(
    val row: String?,
    val seat: String?,
    val block: String?,
    val table: String?
) : Parcelable