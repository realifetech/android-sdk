package com.realifetech.sdk.core.data.fulfilmentPoint

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AutoFill(
    val type: String?,
    val field: String?,
) : Parcelable