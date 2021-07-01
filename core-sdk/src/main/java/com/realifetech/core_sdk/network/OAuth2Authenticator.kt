package com.realifetech.core_sdk.network

import com.realifetech.core_sdk.data.shared.`object`.toBearerFormat
import com.realifetech.core_sdk.domain.AuthenticationToken
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class OAuth2Authenticator(private val accessToken: AuthenticationToken) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        accessToken.ensureActive()
        return response.request
            .newBuilder()
            .header(AUTHORIZATION, accessToken.accessToken.toBearerFormat)
            .build()
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}