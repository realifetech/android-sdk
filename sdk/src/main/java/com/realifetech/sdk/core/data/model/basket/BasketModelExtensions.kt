package com.realifetech.sdk.core.data.model.basket

import com.apollographql.apollo3.api.Optional
import com.realifetech.fragment.FragmentBasket
import com.realifetech.fragment.FragmentModifierItemSelection
import com.realifetech.sdk.core.data.model.fulfilmentPoint.asModel
import com.realifetech.sdk.core.data.model.product.ProductModifierItemSelection
import com.realifetech.sdk.core.data.model.product.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.toLanguage
import com.realifetech.type.BasketInput
import com.realifetech.type.BasketItemInput
import com.realifetech.type.CheckoutInput
import com.realifetech.type.ProductModifierItemSelectionInput

val FragmentBasket.asModel: Basket
    get() = Basket(
        discountAmount = discountAmount,
        grossAmount = grossAmount,
        netAmount = netAmount,
        seatInfo = seatInfo as? LinkedHashMap<String, String>?,
        timeslot = timeslot?.fragmentTimeslot?.asModel,
        collectionDate = collectionDate,
        collectionPreferenceType = collectionPreferenceType,
        items = items?.mapNotNull { it?.asModel }
    )

val FragmentBasket.Item.asModel: BasketItem
    get() = BasketItem(
        id = id,
        price = price,
        modifierItemsPrice = modifierItemsPrice,
        quantity = quantity,
        totalPrice = totalPrice,
        product = product?.fragmentProduct?.asModel,
        productVariant = productVariant?.productVariant?.asModel,
        fulfilmentPoint = fulfilmentPoint?.fragmentFulfilmentPoint?.asModel,
        productModifierItems = productModifierItems?.map { it?.fragmentModifierItemSelection?.asModel }
    )

val FragmentModifierItemSelection.asModel: ProductModifierItemSelection
    get() = ProductModifierItemSelection(
        productModifierItem = productModifierItem?.modifierItem?.asModel,
        quantity = quantity,
        totalPrice = totalPrice
    )

val BasketRequest.asInputObject: BasketInput
    get() = BasketInput(
        collectionDate = Optional.presentIfNotNull(collectionDate),
        collectionPreferenceType = Optional.presentIfNotNull(collectionPreferenceType),
        timeslot = Optional.presentIfNotNull(timeslotId),
        fulfilmentPoint = fulfilmentPoint.orEmpty(),
        seatInfo = Optional.presentIfNotNull(seatInfo),
        items = convertBasketItemsToInput(items).orEmpty()
    )

fun convertBasketItemsToInput(basketItems: List<BasketRequestItem?>?): List<BasketItemInput?>? =
    basketItems?.map {
        BasketItemInput(
            Optional.presentIfNotNull(it?.id),
            Optional.presentIfNotNull(it?.product),
            Optional.presentIfNotNull(it?.productVariant),
            Optional.presentIfNotNull(it?.fulfilmentPoint),
            Optional.presentIfNotNull(it?.quantity),
            Optional.presentIfNotNull(it?.productModifierItems?.toInputList())
        )
    }

val CheckoutRequest.asInput
    get() =
        CheckoutInput(
            Optional.presentIfNotNull(netAmount),
            Optional.presentIfNotNull(language.toLanguage)
        )

fun List<ProductModifierItemSelectionRequest?>.toInputList(): List<ProductModifierItemSelectionInput?> =
    map {
        ProductModifierItemSelectionInput(
            Optional.presentIfNotNull(it?.productModifierItemId),
            Optional.presentIfNotNull(it?.quantity)
        )
    }