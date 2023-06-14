package com.realifetech.sdk.core.data.model.fulfilmentPoint

import com.realifetech.fragment.FragmentCollectionNotes
import com.realifetech.fragment.FragmentForm
import com.realifetech.fragment.FragmentFulfilmentPoint
import com.realifetech.fragment.FragmentFulfilmentPointCategory
import com.realifetech.sdk.core.data.model.shared.`object`.asModel
import com.realifetech.sdk.core.data.model.shared.translation.EMPTY
import com.realifetech.sdk.core.data.model.shared.translation.StandardTranslation
import com.realifetech.sdk.core.data.model.shared.translation.getTranslationForUserLanguage

val FulfilmentPoint.title: String
    get() = getTranslationForUserLanguage()?.title ?: EMPTY

val FulfilmentPoint.description
    get() = getTranslationForUserLanguage()?.description ?: EMPTY

val FulfilmentPointCategory.name: String
    get() = getTranslationForUserLanguage()?.title ?: EMPTY

val FragmentFulfilmentPoint.asModel: FulfilmentPoint
    get() =
        FulfilmentPoint(
            id = id,
            externalId = externalId,
            reference = reference,
            type = type,
            status = status,
            imageUrl = imageUrl,
            mapImageUrl = mapImageUrl,
            lat = lat.toString(),
            long = long.toString(),
            waitTime = waitTime,
            prepTime = prepTime,
            position = position,
            createdAt = createdAt,
            updatedAt = updatedAt,
            translations = translations?.mapNotNull { it?.asModel },
            form = seatForm?.fragmentForm?.asModel,
            categories = categories?.mapNotNull { it?.asModel },
            timeslots = timeslots?.mapNotNull { it?.fragmentTimeslot?.asModel }
        )

val FragmentForm.asModel: Form
    get() = Form(id = id,
        reference = reference,
        imageUrl = imageUrl,
        completionButtonUrl = completionButtonUrl,
        completionButtonTitle = completionButtonTitle,
        showCompletionDate = showCompletionDate,
        allowUpdate = allowUpdate,
        refreshOnSuccess = refreshOnSuccess,
        validationIntegration = validationIntegration,
        expiresIn = expiresIn,
        translations = translations.map { it?.asModel },
        fields = fields?.map { it?.asModel }
    )

val FragmentForm.Field.asModel: Field
    get() = Field(
        type = type,
        key = key,
        validationRegex = validationRegex,
        required = required,
        sortId = sortId,
        autoFill = autoFill?.asModel,
        translations = translations?.map { it?.asModel },
        selectOptions = selectOptions?.map { it?.asModel }
    )

val FragmentForm.SelectOption.asModel: SelectOption
    get() = SelectOption(title = title, value = value, iconUrl = iconUrl)

val FragmentForm.Translation1.asModel: FieldTranslation
    get() = FieldTranslation(
        title = title,
        language = language,
        description = description,
        completionDescription = completionDescription,
        skipButtonTitle = skipButtonTitle,
        completionTitle = completionTitle,
        completionButtonTitle = completionButtonTitle,
        submitButtonTitle = submitButtonTitle
    )

val FragmentForm.AutoFill.asModel: AutoFill
    get() = AutoFill(type = type, field = field)

val FragmentForm.Translation.asModel: FormTranslation
    get() = FormTranslation(
        language = language,
        title = title,
        description = description,
        submitButtonTitle = submitButtonTitle,
        skipButtonTitle = skipButtonTitle,
        completionButtonTitle = completionButtonTitle,
        completionTitle = completionTitle,
        completionDescription = completionDescription
    )

val FragmentFulfilmentPoint.Translation.asModel: FulfilmentPointTranslation
    get() = FulfilmentPointTranslation(
        language = language?.rawValue,
        title = title,
        description = description,
        collectionNote = collectionNote,
        collectionNotes = collectionNotes?.fragmentCollectionNotes?.asModel
    )

val FragmentCollectionNotes.asModel: CollectionNotes
    get() = CollectionNotes(
        virtualQueueCheckin = vIRTUAL_QUEUE_CHECKIN,
        virtualQueuePreOrder = vIRTUAL_QUEUE_PREORDER
    )

val FragmentFulfilmentPoint.Category.asModel: FulfilmentPointCategory
    get() = fragmentFulfilmentPointCategory.asModel

val FragmentFulfilmentPointCategory.Translation.asModel: StandardTranslation
    get() = StandardTranslation(
        id = null,
        language = language?.rawValue,
        title = title
    )


val FragmentFulfilmentPointCategory.asModel: FulfilmentPointCategory
    get() = FulfilmentPointCategory(
        id = id,
        reference = reference,
        status = status,
        iconImageUrl = iconImageUrl,
        position = position,
        createdAt = createdAt,
        updatedAt = updatedAt,
        translations = translations?.mapNotNull { it?.asModel }
    )
