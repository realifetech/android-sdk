package com.realifetech.core_sdk.data.venue

import com.realifetech.fragment.FragmentFulfilmentPoint

val FragmentFulfilmentPoint.Venue.asModel: Venue
    get() = with(fragments.fragmentVenue) {
        Venue(
            id = id,
            name = name,
            isDefault = isDefault,
            label = label,
            description = description,
            imageUrl = imageUrl,
            mapImageUrl = mapImageUrl,
            geoLat = geoLat.toString(),
            geoLong = geoLong.toString(),
            geoLatNorthWest = geoLatNorthWest.toString(),
            geoLatSouthEast = geoLatSouthEast.toString(),
            geoLongNorthWest = geoLongNorthWest.toString(),
            geoLongSouthEast = geoLongSouthEast.toString(),
            city = city,
            usefulInfo = null,
            socialMedia = null,
            status = status,
            externalId = externalId,
            createdAt = createdAt,
            updatedAt = updatedAt,
            venueIconUrl = venueIconUrl
        )
    }