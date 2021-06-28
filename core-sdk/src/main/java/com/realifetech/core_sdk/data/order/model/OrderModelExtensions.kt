package com.realifetech.core_sdk.data.order.model

import com.realifetech.core_sdk.data.basket.model.asModel
import com.realifetech.core_sdk.data.fulfilmentPoint.asModel
import com.realifetech.core_sdk.data.product.asModel
import com.realifetech.core_sdk.data.shared.`object`.asModel
import com.realifetech.fragment.FragmentOrder
import com.realifetech.fragment.FragmentSeatInfo
import com.realifetech.fragment.FragmentUser

val FragmentOrder.asModel: Order
    get() = Order(
        id = id,
        orderNumber = orderNumber.toInt(),
        qrCodeUrl = qrCodeUrl,
        collectionDate = collectionDate,
        status = status,
        state = state?.asModel,
        paymentStatus = paymentStatus,
        grossAmount = grossAmount,
        discountAmount = discountAmount,
        netAmount = netAmount,
        estimatedAt = estimatedAt,
        createdAt = createdAt,
        updatedAt = updatedAt,
        collectionPreferenceType = collectionPreferenceType,
        seatInfo = seatInfo?.fragments?.fragmentSeatInfo?.asModel,
        items = items?.map { it?.asModel }?.toMutableList(),
        orderNotes = orderNotes?.map { it?.asModel },
        timeSlot = timeslot?.fragments?.fragmentTimeslot?.asModel,
        fulfilmentPoint = fulfilmentPoint?.fragments?.fragmentFulfilmentPoint?.asModel,
        user = user?.fragments?.fragmentUser?.asModel
    )

val FragmentSeatInfo.asModel: SeatInfo
    get() = SeatInfo(row = row, seat = seat, block = block, table = table)

val FragmentUser.asModel: User
    get() = User(
        id = id,
        email = email,
        token = token,
        authType = authType,
        status = status,
        lastLogin = lastLogin,
        ticketsFetchedAt = ticketsFetchedAt,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        phone = phone,
        dob = dob,
        userConsent = userConsent
    )

val FragmentOrder.OrderNote.asModel: OrderNote
    get() = OrderNote(
        id = id,
        note = note,
        author = author
    )

val FragmentOrder.State.asModel: OrderState
    get() = OrderState(
        status = status,
        workingState = workingState,
        translations = translations?.map { it?.asModel }
    )

val FragmentOrder.Translation.asModel: OrderStateTranslation
    get() = OrderStateTranslation(
        id = null,
        language = language,
        title = title,
        description = description
    )

val FragmentOrder.Item.asModel: OrderItem
    get() = OrderItem(
        id = id,
        product = product?.fragments?.fragmentProduct?.asModel,
        productVariant = productVariant?.fragments?.productVariant?.asModel,
        fulfilmentPoint = fulfilmentPoint?.fragments?.fragmentFulfilmentPoint?.asModel,
        productModifierItems = productModifierItems?.map { it?.fragments?.fragmentModifierItemSelection?.asModel },
        price = price,
        modifierItemsPrice = modifierItemsPrice,
        quantity = quantity,
        totalPrice = totalPrice,
        title = title,
        subTitle = subtitle,
        imageUrl = imageUrl
    )