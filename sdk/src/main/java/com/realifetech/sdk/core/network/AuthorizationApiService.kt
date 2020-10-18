package com.realifetech.sdk.core.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthorizationApiService {
    @POST("/v3/oauth/v2/token")
    fun getAuthToken(@Body request: AccessTokenBody): Call<OAuthTokenResponse>
}