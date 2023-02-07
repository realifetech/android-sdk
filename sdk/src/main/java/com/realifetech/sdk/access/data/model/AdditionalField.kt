package com.realifetech.sdk.access.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdditionalField(
    val name: String?,
    val value: String?
) : Parcelable
