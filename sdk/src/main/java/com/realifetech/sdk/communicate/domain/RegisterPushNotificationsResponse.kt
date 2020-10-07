package com.realifetech.sdk.communicate.domain

import com.google.gson.annotations.SerializedName

internal data class RegisterPushNotificationsResponse(
    val isSuccess: Boolean = false,
    @SerializedName("player_id") val playerId: String? = null
)