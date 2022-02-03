package com.realifetech.sdk.campaignautomation.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Content(
    val contentType: ContentType,
    val data: String
) : Parcelable


