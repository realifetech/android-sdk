package com.realifetech.core_sdk.di

import com.realifetech.core_sdk.data.auth.AuthenticationBackendApiDataSource
import com.realifetech.core_sdk.data.auth.AuthenticationTokenPreferenceStorage
import com.realifetech.core_sdk.data.auth.AuthenticationTokenStorage
import com.realifetech.core_sdk.domain.AuthenticationToken
import com.realifetech.core_sdk.network.DeviceIdInterceptor
import com.realifetech.core_sdk.network.OAuth2AuthenticationInterceptor
import com.realifetech.core_sdk.network.OAuth2Authenticator
import com.realifetech.core_sdk.network.graphQl.GraphQlHeadersInterceptor

object CoreProvider {

    val oAuth2Authenticator: OAuth2Authenticator
        get() {
            return OAuth2Authenticator(authenticationToken)
        }

    val oAuthInterceptor: OAuth2AuthenticationInterceptor
        get() {
            return OAuth2AuthenticationInterceptor(authenticationToken)
        }

    fun getDeviceIdInterceptor(deviceId: String): DeviceIdInterceptor =
        DeviceIdInterceptor(deviceId)

    val authenticationToken: AuthenticationToken
        get() {
            return AuthenticationToken(
                authenticationTokenStorage,
                AuthenticationBackendApiDataSource()
            )
        }

    val graphQlInterceptor: GraphQlHeadersInterceptor
        get() {
            return GraphQlHeadersInterceptor(authenticationToken)
        }

    private val authenticationTokenStorage: AuthenticationTokenStorage
        get() {
            return AuthenticationTokenStorage(AuthenticationTokenPreferenceStorage())
        }
}