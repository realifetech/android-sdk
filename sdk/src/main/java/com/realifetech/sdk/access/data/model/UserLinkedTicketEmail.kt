package com.realifetech.sdk.access.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLinkedTicketEmail(
    val id: String?,
    val email: String?,
    val valid: Boolean?
) : Parcelable
