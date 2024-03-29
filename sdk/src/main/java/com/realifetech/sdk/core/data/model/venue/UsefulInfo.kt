package com.realifetech.sdk.core.data.model.venue

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsefulInfo(
    var title: String?,
    var subtitle: String?,
    var imageUrl: String?,
    var iconUrl: String?,
    var url: String?
) : Parcelable