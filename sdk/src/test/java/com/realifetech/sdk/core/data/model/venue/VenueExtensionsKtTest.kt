package com.realifetech.sdk.core.data.model.venue

import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.expectedVenue
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.fragmentVenue
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.venueWithUsefulInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class VenueExtensionsKtTest {

    @Test
    fun getAsModel() {
        val venue = fragmentVenue.asModel
        assertEquals(expectedVenue, venue)
        assertEquals(expectedVenue.isDefault, venue.isDefault)
    }

    @Test
    fun `verify venue params`() {
        val venue = fragmentVenue.asModel
        assertVenueIsCorrect(expectedVenue, venue)

    }

    @Test
    fun `verify venue with useful Info params`() {

        assertVenueIsCorrect(venueWithUsefulInfo, venueWithUsefulInfo)
        val usefulInfo = venueWithUsefulInfo.usefulInfo?.first()
        assertNotNull(usefulInfo)
        assertEquals(
            venueWithUsefulInfo.usefulInfo?.first()?.iconUrl,
            usefulInfo?.iconUrl
        )
        assertEquals(
            venueWithUsefulInfo.usefulInfo?.first()?.imageUrl,
            usefulInfo?.imageUrl
        )
        assertEquals(
            venueWithUsefulInfo.usefulInfo?.first()?.title,
            usefulInfo?.title
        )
        assertEquals(
            venueWithUsefulInfo.usefulInfo?.first()?.subtitle,
            usefulInfo?.subtitle
        )
        assertEquals(
            venueWithUsefulInfo.usefulInfo?.first()?.url,
            usefulInfo?.url
        )

    }

    private fun assertVenueIsCorrect(expectedVenue: Venue, venue: Venue) {
        assertEquals(expectedVenue.id, venue.id)
        assertEquals(expectedVenue.isDefault, venue.isDefault)
        assertEquals(expectedVenue.name, venue.name)
        assertEquals(expectedVenue.label, venue.label)
        assertEquals(expectedVenue.description, venue.description)
        assertEquals(expectedVenue.imageUrl, venue.imageUrl)
        assertEquals(expectedVenue.mapImageUrl, venue.mapImageUrl)
        assertEquals(expectedVenue.geoLat, venue.geoLat)
        assertEquals(expectedVenue.geoLong, venue.geoLong)
        assertEquals(expectedVenue.geoLatNorthWest, venue.geoLatNorthWest)
        assertEquals(expectedVenue.geoLongNorthWest, venue.geoLongNorthWest)
        assertEquals(expectedVenue.geoLatSouthEast, venue.geoLatSouthEast)
        assertEquals(expectedVenue.geoLongSouthEast, venue.geoLongSouthEast)
        assertEquals(expectedVenue.city, venue.city)
        assertEquals(expectedVenue.usefulInfo, venue.usefulInfo)
        assertEquals(expectedVenue.socialMedia, venue.socialMedia)
        assertEquals(expectedVenue.status, venue.status)
        assertEquals(expectedVenue.externalId, venue.externalId)
        assertEquals(expectedVenue.createdAt, venue.createdAt)
        assertEquals(expectedVenue.updatedAt, venue.updatedAt)
        assertEquals(expectedVenue.venueIconUrl, venue.venueIconUrl)
    }
}