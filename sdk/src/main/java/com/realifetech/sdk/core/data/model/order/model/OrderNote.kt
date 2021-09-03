package com.realifetech.sdk.core.data.model.order.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderNote(
    val id: Int?,
    val note: String?,
    val author: String?
) : Parcelable