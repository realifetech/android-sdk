package com.realifetech.sdk.core.data.model.order.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserConsent(
    val id: String,
    val marketingConsent: Boolean?,
    val analysisConsent: Boolean?
) : Parcelable