package com.realifetech.core_sdk.data.order.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserConsent(
    val id: String,
    val marketingConsent: Boolean?,
    val analysisConsent: Boolean?
) : Parcelable