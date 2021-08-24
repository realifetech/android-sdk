package com.realifetech.sdk.core.data.fulfilmentPoint

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FieldTranslation(
    val title: String?,
    val language: String,
    val description: String?,
    val completionDescription: String?,
    val skipButtonTitle: String?,
    val completionTitle: String?,
    val completionButtonTitle: String?,
    val submitButtonTitle: String?,
) : Parcelable