package com.realifetech.sdk.core.data.model.fulfilmentPoint

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectOption(
    val title: String?,
    val value: String?,
    val iconUrl: String?,
) : Parcelable
