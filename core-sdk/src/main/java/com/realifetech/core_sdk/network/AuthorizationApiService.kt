package com.realifetech.core_sdk.network

import com.realifetech.core_sdk.data.auth.OAuthTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthorizationApiService {
    @POST("/v3/oauth/v2/token")
    fun getAuthToken(@Body request: AccessTokenBody): Call<OAuthTokenResponse>

    @POST("/v3/oauth/v2/token")
    fun refreshAuthToken(@Body refreshTokenBody: RefreshTokenBody): Call<OAuthTokenResponse>

}