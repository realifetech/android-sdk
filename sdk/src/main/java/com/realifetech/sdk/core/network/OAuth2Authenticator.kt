package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.data.model.shared.`object`.toBearerFormat
import com.realifetech.sdk.core.domain.OAuthManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class OAuth2Authenticator :
    Authenticator {
    private var oAuthManager: OAuthManager? = null
    override fun authenticate(route: Route?, response: Response): Request {
        val accessToken = getOAuthManager()
        accessToken.ensureActive()
        return response.request
            .newBuilder()
            .header(AUTHORIZATION, accessToken.accessToken.toBearerFormat)
            .build()
    }
    private fun getOAuthManager(): OAuthManager {
        if (oAuthManager == null) {
            oAuthManager = OAuthManager()
        }
        return oAuthManager as OAuthManager
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}