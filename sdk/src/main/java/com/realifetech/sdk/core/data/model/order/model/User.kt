package com.realifetech.sdk.core.data.model.order.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String,
    val email: String?,
    val token: String?,
    val authType: String?,
    val status: String?,
    val lastLogin: String?,
    val ticketsFetchedAt: String?,
    val firstName: String?,
    val lastName: String?,
    val gender: String?,
    val phone: String?,
    val dob: String?,
    val userConsent: UserConsent?
) : Parcelable
