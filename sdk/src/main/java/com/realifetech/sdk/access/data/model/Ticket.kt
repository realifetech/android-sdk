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
    val additionalFields: List<AdditionalField?>?,
    val printed: Boolean?
) : Parcelable


