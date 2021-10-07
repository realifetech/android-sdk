package com.realifetech.sdk.core.data.model.basket

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
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
        timeslot = timeslot?.fragments?.fragmentTimeslot?.asModel,
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
        product = product?.fragments?.fragmentProduct?.asModel,
        productVariant = productVariant?.fragments?.productVariant?.asModel,
        fulfilmentPoint = fulfilmentPoint?.fragments?.fragmentFulfilmentPoint?.asModel,
        productModifierItems = productModifierItems?.map { it?.fragments?.fragmentModifierItemSelection?.asModel }
    )

val FragmentModifierItemSelection.asModel: ProductModifierItemSelection
    get() = ProductModifierItemSelection(
        productModifierItem = productModifierItem?.fragments?.modifierItem?.asModel,
        quantity = quantity,
        totalPrice = totalPrice
    )

val BasketRequest.asInputObject: BasketInput
    get() = BasketInput(
        collectionDate = collectionDate.toInput(),
        collectionPreferenceType = collectionPreferenceType.toInput(),
        timeslot = timeslotId.toInput(),
        fulfilmentPoint = fulfilmentPoint.orEmpty(),
        seatInfo = seatInfo.toInput(),
        items = convertBasketItemsToInput(items).orEmpty()
    )

fun convertBasketItemsToInput(basketItems: List<BasketRequestItem?>?): List<BasketItemInput?>? =
    basketItems?.map {
        BasketItemInput(
            Input.optional(it?.id),
            Input.optional(it?.product),
            Input.optional(it?.productVariant),
            Input.optional(it?.fulfilmentPoint),
            Input.optional(it?.quantity),
            Input.optional(it?.productModifierItems?.toInputList())
        )
    }

val CheckoutRequest.asInput
    get() =
        CheckoutInput(
            Input.optional(netAmount),
            Input.optional(language.toLanguage)
        )

fun List<ProductModifierItemSelectionRequest?>.toInputList(): List<ProductModifierItemSelectionInput?> =
    map {
        ProductModifierItemSelectionInput(
            Input.optional(it?.productModifierItemId),
            Input.optional(it?.quantity)
        )
    }