package com.realifetech.sdk.access.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketAuth(
    val id: Int?,
    val userEmail: UserLinkedTicketEmail?,
    val ticketIntegration: TicketIntegration?,
    val accessToken: String?,
    val refreshToken: String?,
    val expireAt: String?,
    val clientEmail: String?,
    val clientId: String?
) : Parcelable