package com.realifetech.sdk.core.data.model.shared.mocks

import com.realifetech.fragment.FragmentFulfilmentPoint
import com.realifetech.fragment.FragmentFulfilmentPoint.Venue.Fragments
import com.realifetech.fragment.FragmentMutationResponse
import com.realifetech.fragment.FragmentTimeslot
import com.realifetech.fragment.FragmentVenue
import com.realifetech.sdk.core.data.model.shared.`object`.TimeSlot
import com.realifetech.sdk.core.data.model.shared.`object`.TimeSlotTranslation
import com.realifetech.sdk.core.data.model.venue.SocialMediaResponse
import com.realifetech.sdk.core.data.model.venue.UsefulInfo
import com.realifetech.sdk.core.data.model.venue.Venue
import com.realifetech.type.Language

object SharedMocks {
    val mutationResponse = FragmentMutationResponse("", true, "success")
    const val token = "ab12salew0543fg2fds"
    const val languageText = "en"
    const val randomText = "batata"
    const val appCode = "LS"
    const val clientId = "LS_0"
    const val bearerFormat = "Bearer $token"
    val fragmentTimeslot = generateFragmentTimeSlot(false)
    val timeslot = generateTimeSlot(false)
    val fragmentTimeslotWithNullTranslations = generateFragmentTimeSlot(true)
    val timeslotWithNullTranslations = generateTimeSlot(true)

    private fun generateTimeSlot(translationsAreNull: Boolean): TimeSlot =
        TimeSlot(
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            if (translationsAreNull) null else
                listOf(
                    TimeSlotTranslation(
                        Language.EN.rawValue,
                        "test title",
                        "test test",
                        "test"
                    )
                )
        )

    private fun generateFragmentTimeSlot(translationsAreNull: Boolean): FragmentTimeslot =
        FragmentTimeslot(
            "",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            if (translationsAreNull) null else listOf(
                FragmentTimeslot.Translation(
                    "",
                    Language.EN,
                    "test title",
                    "test test",
                    "test"
                )
            )
        )

    val fragmentVenue =
        FragmentFulfilmentPoint.Venue(
            "", Fragments(
                FragmentVenue(
                    "",
                    "2",
                    "label",
                    "batata",
                    "Active",
                    true,
                    "who cares",
                    "www.google.com",
                    "maps.google.com",
                    1234.0,
                    9876.5,
                    2904.4,
                    9941.2,
                    23.3,
                    21.3,
                    "Batata Land",
                    "23",
                    "2021-12-10 07:40:48 AM",
                    "2021-12-10 07:40:49 AM",
                    "www.facebook.com"
                )
            )
        )
    val expectedVenue = Venue(
        "2",
        true,
        "batata",
        "label",
        "who cares",
        "www.google.com",
        "maps.google.com",
        "1234.0",
        "9876.5",
        "2904.4",
        "9941.2",
        "23.3",
        "21.3",
        "Batata Land",
        null,
        null,
        "Active",
        "23",
        "2021-12-10 07:40:48 AM",
        "2021-12-10 07:40:49 AM",
        "www.facebook.com"
    )

    val venueWithUsefulInfo = Venue(
        id = "2",
        name = "batata",
        label = "label",
        description = "who cares",
        imageUrl = "www.google.com",
        mapImageUrl = "maps.google.com",
        geoLat = "1234.0",
        geoLong = "9876.5",
        geoLatNorthWest = "2904.4",
        geoLongNorthWest = "9941.2",
        geoLatSouthEast = "23.3",
        geoLongSouthEast = "21.3",
        city = "Batata Land",
        usefulInfo = listOf(
            UsefulInfo(
                "useful",
                "info",
                "useful.info.com",
                "info.com",
                "useful.com"
            )
        ),
        socialMedia = listOf(SocialMediaResponse(1, "test", "test.com", "imageTest.com")),
        status = "Active",
        externalId = "23",
        createdAt = "2021-12-10 07:40:48 AM",
        updatedAt = "2021-12-10 07:40:49 AM",
        venueIconUrl = "www.facebook.com"
    )
}