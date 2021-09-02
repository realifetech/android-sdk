package com.realifetech.sdk.communicate.data

import com.google.gson.annotations.SerializedName

data class RegisterPushNotificationsResponse(
    val isSuccess: Boolean = false,
    @SerializedName("player_id") val playerId: String? = null
)