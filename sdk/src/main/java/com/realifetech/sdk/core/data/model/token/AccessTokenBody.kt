package com.realifetech.sdk.core.data.model.token

import com.google.gson.annotations.SerializedName

class AccessTokenBody(
        @SerializedName("grant_type") val grantType: String = "client_credentials",
        @SerializedName("client_id") val clientId: String = "",
        @SerializedName("client_secret") val clientSecret: String
)
