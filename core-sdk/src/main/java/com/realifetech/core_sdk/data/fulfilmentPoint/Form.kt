package com.realifetech.core_sdk.data.fulfilmentPoint

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Form(
    val id: String,
    val reference: String?,
    val imageUrl: String?,
    val completionButtonUrl: String?,
    val completionButtonTitle: String?,
    val showCompletionDate: Boolean?,
    val allowUpdate: Boolean?,
    val refreshOnSuccess: Boolean?,
    val validationIntegration: String?,
    val expiresIn: Int?,
    val translations: List<FormTranslation?>,
    val fields: List<Field?>?
) : Parcelable