package com.realifetech.core_sdk.data.venue

import android.os.Parcelable
import com.realifetech.core_sdk.data.shared.`object`.WidgetItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Venue(
    var id: String = "",
    val isDefault: Boolean? = false,
    var name: String? = "",
    val label: String? = "",
    val description: String? = "",
    val imageUrl: String? = "",
    val mapImageUrl: String? = "",
    val geoLat: String? = "",
    val geoLong: String? = "",
    val geoLatNorthWest: String? = "",
    val geoLongNorthWest: String? = "",
    val geoLatSouthEast: String? = "",
    val geoLongSouthEast: String? = "",
    val city: String? = "",
    val usefulInfo: List<UsefulInfo>? = emptyList(),
    val socialMedia: List<SocialMediaResponse>? = emptyList(),
    val status: String? = null,
    val externalId: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val venueIconUrl: String? = null
) : WidgetItem(), Parcelable {

    constructor(selectedVenueId: Int) : this() {
        this.id = selectedVenueId.toString()
    }
}
