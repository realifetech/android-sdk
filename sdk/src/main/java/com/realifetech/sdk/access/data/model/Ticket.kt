package com.realifetech.sdk.access.data.model

import android.os.Parcelable
import com.realifetech.fragment.FragmentTicket
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    val id: String?,
    val seat: String?,
    val qrCodeUrl: String?,
    val sessionDate: String?,
    val title: String?,
    val eventUId: String?,
    val barCode: String?,
    val sectorName: String?,
    val venueName: String?,
    val venueRoom: String?,
    val clientName: String?,
    val section: String?,
    val row: String?,
    val clientEmail: String?,
    val price: Int?,
    val currency: Currency?,
    val externalCustomerRef: String?,
    val externalCardRef: String?,
    val entrance: String?,
    val shareLink: String?,
    val canShare: Boolean?,
    val legalLongText: String?,
    val legalShortText: String?,
    val mapImageUrl: String?,
    val mapUrl: String?,
    val status: String?,
    val redeemedAt: String?,
    val redeemerEmail: String?,
    val sharerEmail: String?,
    val additionalFields: ArrayList<AdditionalField>?,
    val printed: Boolean?
) : Parcelable

fun Ticket.fromFragmentTicket(fragmentTicket: FragmentTicket) =
    FragmentTicket(
        id = fragmentTicket.id,
        seat = fragmentTicket.seat,
        qrCodeUrl = fragmentTicket.qrCodeUrl,
        sessionDate = fragmentTicket.sessionDate,
        title = fragmentTicket.title,
        eventUId = fragmentTicket.eventUId,
        barCode = fragmentTicket.barCode,
        sectorName = fragmentTicket.sectorName,
        venueName = fragmentTicket.venueName,
        venueRoom = fragmentTicket.venueRoom,
        clientName = fragmentTicket.clientName,
        section = fragmentTicket.section,
        row = fragmentTicket.row,
        clientEmail = fragmentTicket.clientEmail,
        price = fragmentTicket.price,
        currency = fragmentTicket.currency,
        externalCustomerRef = fragmentTicket.externalCustomerRef,
        externalCardRef = fragmentTicket.externalCardRef,
        entrance = fragmentTicket.entrance,
        shareLink = fragmentTicket.shareLink,
        canShare = fragmentTicket.canShare,
        legalLongText = fragmentTicket.legalLongText,
        legalShortText = fragmentTicket.legalShortText,
        mapImageUrl = fragmentTicket.mapImageUrl,
        mapUrl = fragmentTicket.mapUrl,
        status = fragmentTicket.status,
        redeemedAt = fragmentTicket.redeemedAt,
        redeemerEmail = fragmentTicket.redeemerEmail,
        sharerEmail = fragmentTicket.sharerEmail,
        additionalFields = fragmentTicket.additionalFields,
        printed = fragmentTicket.printed
    )

