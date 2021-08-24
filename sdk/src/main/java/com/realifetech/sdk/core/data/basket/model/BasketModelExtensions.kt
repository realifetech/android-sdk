package com.realifetech.sdk.core.data.basket.model

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.realifetech.fragment.FragmentBasket
import com.realifetech.sdk.core.data.product.ProductModifierItemSelection
import com.realifetech.fragment.FragmentModifierItemSelection
import com.realifetech.sdk.core.data.fulfilmentPoint.asModel
import com.realifetech.sdk.core.data.product.asModel
import com.realifetech.sdk.core.data.shared.`object`.asModel
import com.realifetech.type.*

val FragmentBasket.asModel: Basket
    get() = Basket(
        discountAmount = discountAmount,
        grossAmount = grossAmount,
        netAmount = netAmount,
        seatInfo = seatInfo?.asModel,
        timeslot = timeslot?.fragments?.fragmentTimeslot?.asModel,
        collectionDate = collectionDate,
        collectionPreferenceType = collectionPreferenceType,
        items = items?.mapNotNull { it?.asModel }
    )

val FragmentBasket.SeatInfo.asModel
    get() = SeatInfo(
        row = fragments.fragmentSeatInfo.row,
        seat = fragments.fragmentSeatInfo.seat,
        block = fragments.fragmentSeatInfo.block,
        table = fragments.fragmentSeatInfo.table
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
        seatInfo = seatInfo?.asInput.toInput(),
        items = convertBasketItemsToInput(items).orEmpty()
    )

fun convertBasketItemsToInput(basketItems: List<BasketRequestItem?>?): List<BasketItemInput?>? =
    basketItems?.map {
        BasketItemInput(
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
            Input.optional(Language.valueOf(language))
        )

fun List<com.realifetech.sdk.core.data.basket.model.ProductModifierItemSelection?>.toInputList(): List<ProductModifierItemSelectionInput?> =
    map {
        ProductModifierItemSelectionInput(
            Input.optional(it?.productModifierItemId),
            Input.optional(it?.quantity)
        )
    }

val SeatInfo.asInput
    get() = SeatInfoInput(
        row = Input.optional(row),
        seat = Input.optional(seat),
        block = Input.optional(block),
        table = Input.optional(table)
    )