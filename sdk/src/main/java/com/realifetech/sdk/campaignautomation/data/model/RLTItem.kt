package com.realifetech.sdk.campaignautomation.data.model


import android.os.Parcelable
import com.realifetechCa.type.ContentType
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class RLTItem(
    val contentType: ContentType?,
    val data: @RawValue RLTDataModel?
) : Parcelable