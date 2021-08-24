package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.data.shared.`object`.toBearerFormat
import com.realifetech.sdk.core.domain.AuthenticationToken
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