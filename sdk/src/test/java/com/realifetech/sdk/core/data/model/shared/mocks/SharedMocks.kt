package com.realifetech.sdk.core.data.model.shared.mocks

import com.realifetech.fragment.FragmentMutationResponse
import com.realifetech.fragment.FragmentTimeslot
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.shared.`object`.TimeSlot
import com.realifetech.sdk.core.data.model.shared.`object`.TimeSlotTranslation
import com.realifetech.sdk.core.data.model.shared.`object`.WidgetItem
import com.realifetech.type.Language

object SharedMocks {
    val mutationResponse = FragmentMutationResponse("", true, "success")
    val token = "ab12salew0543fg2fds"
    val languageText = "en"
    val randomText = "batata"
    val bearerFormat = "Bearer $token"
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
}