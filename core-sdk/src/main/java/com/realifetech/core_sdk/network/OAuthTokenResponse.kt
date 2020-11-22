package com.realifetech.core_sdk.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class OAuthTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("refresh_expiry") val refreshExpiry: Date?
)
