package com.realifetech.core_sdk.data.order.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderNote(
    val id: Int?,
    val note: String?,
    val author: String?
) : Parcelable