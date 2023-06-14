package com.realifetech.sdk.core.data.model.order.model

import com.realifetech.fragment.FragmentOrder
import com.realifetech.fragment.FragmentUser
import com.realifetech.sdk.core.data.model.basket.asModel
import com.realifetech.sdk.core.data.model.fulfilmentPoint.asModel
import com.realifetech.sdk.core.data.model.product.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.asModel

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
        seatInfo = seatInfo as? LinkedHashMap<String, String>?,
        items = items?.map { it?.asModel }?.toMutableList(),
        orderNotes = orderNotes?.map { it?.asModel },
        timeSlot = timeslot?.fragmentTimeslot?.asModel,
        fulfilmentPoint = fulfilmentPoint?.fragmentFulfilmentPoint?.asModel,
        user = user?.fragmentUser?.asModel
    )

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
        userConsent = userConsent?.asModel
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
        product = product?.fragmentProduct?.asModel,
        productVariant = productVariant?.productVariant?.asModel,
        fulfilmentPoint = fulfilmentPoint?.fragmentFulfilmentPoint?.asModel,
        productModifierItems = productModifierItems?.map { it?.fragmentModifierItemSelection?.asModel },
        price = price,
        modifierItemsPrice = modifierItemsPrice,
        quantity = quantity,
        totalPrice = totalPrice,
        title = title,
        subTitle = subtitle,
        imageUrl = imageUrl
    )

val FragmentUser.UserConsent.asModel: UserConsent
    get() = UserConsent(
        id = id,
        marketingConsent = marketingConsent,
        analysisConsent = analysisConsent
    )