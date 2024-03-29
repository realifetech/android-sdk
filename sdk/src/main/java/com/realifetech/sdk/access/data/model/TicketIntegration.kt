package com.realifetech.sdk.access.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketIntegration(
    val id: Int?,
    val name: String?
) : Parcelable
