package com.realifetech.sdk.campaignautomation.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Banner (
    val id: Int,
    val title: String?,
    val subtitle: String?,
    val url: String?,
    val imageUrl: String?,
    val language: String?
) : Parcelable, RLTDataModel