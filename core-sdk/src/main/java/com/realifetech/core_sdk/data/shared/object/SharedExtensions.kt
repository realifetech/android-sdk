package com.realifetech.core_sdk.data.shared.`object`

import com.realifetech.fragment.FragmentMutationResponse
import com.realifetech.fragment.FragmentTimeslot
import com.realifetech.type.Language

val FragmentMutationResponse.asModel: StandardResponse
    get() = StandardResponse(
        code = null,
        type = null,
        message = message
    )

val String.toLanguage: Language?
    get() = Language.values().firstOrNull { it.name == this }

val FragmentTimeslot.asModel: TimeSlot
    get() = TimeSlot(
        id = id,
        translations = translations?.mapNotNull { it?.asModel },
        externalId = externalId,
        reference = reference,
        startTime = startTime,
        endTime = endTime,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

val FragmentTimeslot.Translation.asModel: TimeSlotTranslation
    get() = TimeSlotTranslation(
        language = language?.rawValue,
        title = title,
        description = description,
        collectionNote = collectionNote
    )
