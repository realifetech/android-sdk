package com.realifetech.sdk.core.di

import com.realifetech.sdk.core.data.auth.AuthenticationBackendApiDataSource
import com.realifetech.sdk.core.data.auth.AuthenticationTokenPreferenceStorage
import com.realifetech.sdk.core.data.auth.AuthenticationTokenStorage
import com.realifetech.sdk.core.domain.AuthenticationToken

object CoreProvider {

    val oAuth2Authenticator: com.realifetech.sdk.core.network.OAuth2Authenticator
        get() {
            return com.realifetech.sdk.core.network.OAuth2Authenticator(authenticationToken)
        }

    val oAuthInterceptor: com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor
        get() {
            return com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor(
                authenticationToken
            )
        }

    fun getDeviceIdInterceptor(deviceId: String): com.realifetech.sdk.core.network.DeviceIdInterceptor =
        com.realifetech.sdk.core.network.DeviceIdInterceptor(deviceId)

    val authenticationToken: AuthenticationToken
        get() {
            return AuthenticationToken(
                authenticationTokenStorage,
                AuthenticationBackendApiDataSource()
            )
        }

    val graphQlInterceptor: com.realifetech.sdk.core.network.graphQl.GraphQlHeadersInterceptor
        get() {
            return com.realifetech.sdk.core.network.graphQl.GraphQlHeadersInterceptor(
                authenticationToken
            )
        }

    private val authenticationTokenStorage: AuthenticationTokenStorage
        get() {
            return AuthenticationTokenStorage(AuthenticationTokenPreferenceStorage())
        }
}