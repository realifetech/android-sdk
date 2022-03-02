package com.realifetech.sdk.campaignautomation.data.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
data class BannerDataModel(
    val id: Int,
    val title: String?,
    val subtitle: String?,
    private val url: String?,
    val imageUrl: String?,
    val language: String?,
) : Parcelable, RLTLinkHandling, RLTDataModel {

    @IgnoredOnParcel
    var listener: (() -> Unit)? = null

    override fun generateLinkHandler(): String? {
        listener?.invoke()
        return this.url
    }

}
