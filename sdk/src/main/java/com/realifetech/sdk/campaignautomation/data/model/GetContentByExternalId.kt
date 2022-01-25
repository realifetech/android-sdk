package com.realifetech.sdk.campaignautomation.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetContentByExternalId(
    val campaignId: String,
    val items: List<Content>
) : Parcelable
