package com.realifetech.sdk.core.di

import com.realifetech.sdk.core.data.AuthenticationBackendApiDataSource
import com.realifetech.sdk.core.data.AuthenticationTokenStorage
import com.realifetech.sdk.core.data.AuthenticationTokenPreferenceStorage
import com.realifetech.sdk.core.domain.AuthenticationToken
import com.realifetech.sdk.core.network.DeviceIdInterceptor
import com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor
import com.realifetech.sdk.core.network.graphQl.GraphQlHeadersInterceptor

internal object CoreProvider {
    val oAuthInterceptor: OAuth2AuthenticationInterceptor
        get() {
            return OAuth2AuthenticationInterceptor(authenticationToken)
        }

    val deviceIdInterceptor: DeviceIdInterceptor
        get() = DeviceIdInterceptor()

    val authenticationToken: AuthenticationToken
        get() {
            return AuthenticationToken(authenticationTokenStorage, AuthenticationBackendApiDataSource())
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