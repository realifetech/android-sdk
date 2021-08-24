package com.realifetech.sdk.core.data.venue

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SocialMediaResponse(
    val id: Int,
    val title: String?,
    val url: String?,
    val iconUrl: String?
) : Parcelable