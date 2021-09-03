package com.realifetech.sdk.core.data.model.fulfilmentPoint

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormTranslation(
    val language: String,
    val title: String?,
    val description: String?,
    val submitButtonTitle: String?,
    val skipButtonTitle: String?,
    val completionButtonTitle: String?,
    val completionTitle: String?,
    val completionDescription: String?
) : Parcelable
