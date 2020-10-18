package com.realifetech.sdk.core.di

import com.realifetech.sdk.core.data.AuthenticationBackendApiDataSource
import com.realifetech.sdk.core.data.AuthenticationTokenStorage
import com.realifetech.sdk.core.data.AuthenticationTokenPreferenceStorage
import com.realifetech.sdk.core.domain.AuthentificationToken
import com.realifetech.sdk.core.network.AuthorizationApiNetwork
import com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor

internal object CoreProvider {
    val oAuthInterceptor: OAuth2AuthenticationInterceptor
        get() {
            return OAuth2AuthenticationInterceptor(authenticationToken)
        }

    private val authenticationToken: AuthentificationToken
        get() {
            return AuthentificationToken(authenticationTokenStorage, AuthenticationBackendApiDataSource())
        }

    private val authenticationTokenStorage: AuthenticationTokenStorage
        get() {
            return AuthenticationTokenStorage(AuthenticationTokenPreferenceStorage())
        }
}