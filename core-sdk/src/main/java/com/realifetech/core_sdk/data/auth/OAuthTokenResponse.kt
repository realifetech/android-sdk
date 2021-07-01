package com.realifetech.core_sdk.data.auth

import com.google.gson.annotations.SerializedName
import java.util.*

data class OAuthTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_expiry") val refreshExpiry: Date?
) {
    val isTokenExpired: Boolean
        get() {
            refreshExpiry?.let {
                val timeNow = Calendar.getInstance().timeInMillis
                return timeNow >= it.time
            }
            return true
        }
}
