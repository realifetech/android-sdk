package com.realifetech.sdk.core.network

import com.google.gson.annotations.SerializedName

internal class AccessTokenBody(
        @SerializedName("grant_type") val grantType: String = "client_credentials",
        @SerializedName("client_id") val clientId: String = "LS_0",
        @SerializedName("client_secret") val clientSecret: String
)
