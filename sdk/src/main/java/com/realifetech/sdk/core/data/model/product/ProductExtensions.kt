package com.realifetech.sdk.core.data.model.product

import com.realifetech.sdk.core.data.model.fulfilmentPoint.asModel
import com.realifetech.sdk.core.data.model.shared.translation.StandardTranslation
import com.realifetech.fragment.FragmentProduct
import com.realifetech.fragment.ModifierItem

val FragmentProduct.asModel: Product
    get() = Product(
        id = id,
        externalId = externalId,
        status = status,
        images = images?.map { it?.asModel }?.toMutableList(),
        translations = translations?.map { it?.asModel }?.toMutableList(),
        categories = categories?.map { it?.asModel }?.toMutableList(),
        variants = variants?.map { it?.fragments?.productVariant?.asModel }
            ?.toMutableList(),
        modifierLists = modifierLists?.map { it?.asModel }?.toMutableList(),
        fulfilmentPoints = fulfilmentPoints?.map { it?.fragments?.fragmentFulfilmentPoint?.asModel }
            ?.toMutableList(),
        reference = reference
    )

val FragmentProduct.Image.asModel: ProductImage
    get() = ProductImage(
        imageUrl = imageUrl,
        position = position
    )

val FragmentProduct.Translation2.asModel: ProductTranslation
    get() = ProductTranslation(
        id = null,
        language = language,
        title = title,
        description = null
    )

val FragmentProduct.Translation1.asModel: StandardTranslation
    get() = StandardTranslation(
        id = null,
        language = language.toString(),
        title = title
    )

val FragmentProduct.Category.asModel: ProductCategory
    get() = ProductCategory(
        id = id,
        externalId = externalId,
        reference = reference,
        status = status,
        position = position,
        translations = translations?.map { it?.asModel }?.toMutableList()
    )

val com.realifetech.fragment.ProductVariant.Translation.asModel: StandardTranslation
    get() =
        StandardTranslation(
            id = null,
            language = language.toString(),
            title = title
        )

val com.realifetech.fragment.ProductVariant.asModel: ProductVariant
    get() = ProductVariant(
        id = id,
        externalId = externalId,
        price = price,
        createdAt = createdAt,
        updatedAt = updatedAt,
        translations = translations?.map { it?.asModel }?.toMutableList()
    )

val ModifierItem.Translation.asModel: StandardTranslation
    get() = StandardTranslation(
        id = null,
        language = language.toString(),
        title = title
    )

val ModifierItem.asModel: ProductModifierItem
    get() = ProductModifierItem(
        id = id,
        status = status,
        externalId = externalId,
        additionalPrice = additionalPrice,
        position = position,
        translations = translations?.map { it?.asModel }?.toMutableList()
    )

val FragmentProduct.Translation.asModel: StandardTranslation
    get() = StandardTranslation(
        id = null,
        language = language.toString(),
        title = title
    )

val FragmentProduct.ModifierList.asModel: ProductModifierList
    get() = ProductModifierList(
        id = id,
        status = status,
        externalId = externalId,
        reference = reference,
        multipleSelect = multipleSelect,
        mandatorySelect = mandatorySelect,
        maxAllowed = maxAllowed,
        items = items?.map { it?.fragments?.modifierItem?.asModel }?.toMutableList(),
        translations = translations?.map { it?.asModel }?.toMutableList()
    )