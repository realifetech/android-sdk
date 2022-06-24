package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.data.model.shared.`object`.toBearerFormat
import com.realifetech.sdk.core.domain.OAuthManager
import dagger.Lazy
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class OAuth2Authenticator @Inject constructor(private val oAuthManager: Lazy<OAuthManager>) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        val accessToken = oAuthManager.get()
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