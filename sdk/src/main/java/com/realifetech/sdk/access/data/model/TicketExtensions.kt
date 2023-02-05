package com.realifetech.sdk.access.data.model

import com.realifetech.fragment.FragmentTicket

val FragmentTicket.asModel: Ticket
    get() = Ticket(
        id = id,
        seat = seat,
        qrCodeUrl = qrCodeUrl,
        sessionDate = sessionDate,
        title = title,
        eventUId = eventUId,
        barCode = barCode,
        sectorName = sectorName,
        venueName = venueName,
        venueRoom = venueRoom,
        clientName = clientName,
        section = section,
        row = row,
        clientEmail = clientEmail,
        price = price,
        currency = currency?.asModel,
        externalCustomerRef = externalCustomerRef,
        externalCardRef = externalCardRef,
        entrance = entrance,
        shareLink = shareLink,
        canShare = canShare,
        legalLongText = legalLongText,
        legalShortText = legalShortText,
        mapImageUrl = mapImageUrl,
        mapUrl = mapUrl,
        status = status,
        redeemedAt = redeemedAt,
        redeemerEmail = redeemerEmail,
        sharerEmail = sharerEmail,
        additionalFields = additionalFields?.map { it?.asModel }?.toMutableList(),
        printed = printed
    )

val FragmentTicket.Currency.asModel: Currency
    get() = Currency(
        id = id,
        title = title,
        isoCode = isoCode,
        sign = sign
    )

val FragmentTicket.AdditionalField.asModel: AdditionalField
    get() = AdditionalField(
        name = name,
        value = value
    )