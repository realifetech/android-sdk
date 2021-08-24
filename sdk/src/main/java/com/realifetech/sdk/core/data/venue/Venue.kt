package com.realifetech.sdk.core.data.venue

import android.os.Parcelable
import com.realifetech.sdk.core.data.shared.`object`.WidgetItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Venue(
    var id: String,
    val isDefault: Boolean? = false,
    var name: String?,
    val label: String?,
    val description: String?,
    val imageUrl: String?,
    val mapImageUrl: String?,
    val geoLat: String?,
    val geoLong: String?,
    val geoLatNorthWest: String?,
    val geoLongNorthWest: String?,
    val geoLatSouthEast: String?,
    val geoLongSouthEast: String?,
    val city: String?,
    val usefulInfo: List<UsefulInfo>?,
    val socialMedia: List<SocialMediaResponse>?,
    val status: String?,
    val externalId: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val venueIconUrl: String?
) : WidgetItem(), Parcelable
