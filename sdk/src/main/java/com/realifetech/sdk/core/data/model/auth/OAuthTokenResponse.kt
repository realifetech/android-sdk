package com.realifetech.sdk.core.data.model.auth

import com.google.gson.annotations.SerializedName
import java.util.*

data class OAuthTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Int, // in seconds
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_expiry") val refreshExpiry: Date?
) {
    var expireAtMilliseconds: Long? = null

    init {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, expiresIn)
        expireAtMilliseconds = calendar.timeInMillis
    }

    val isTokenExpired: Boolean
        get() {
            expireAtMilliseconds?.let {
                val timeNow = Calendar.getInstance().timeInMillis
                return timeNow >= it
            }
            return true
        }
}