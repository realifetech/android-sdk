package com.realifetech.sdk.core.domain

import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
import com.realifetech.sdk.core.data.datasource.AuthApiDataSource
import com.realifetech.sdk.core.data.model.shared.`object`.toClientId
import com.realifetech.sdk.core.data.model.shared.mocks.SharedMocks.token
import com.realifetech.sdk.core.data.model.shared.translation.EMPTY
import com.realifetech.sdk.core.mocks.AuthMocks.accessTokenInfo
import com.realifetech.sdk.core.mocks.AuthMocks.expiredRltToken
import com.realifetech.sdk.core.mocks.AuthMocks.refreshToken
import com.realifetech.sdk.core.mocks.NetworkMocks.rltToken
import dagger.Lazy
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class OAuthManagerTest {


    @RelaxedMockK
    lateinit var authTokenStorage: AuthTokenStorage

    @RelaxedMockK
    lateinit var authApiLazyWrapper: Lazy<AuthApiDataSource>

    @RelaxedMockK
    lateinit var platformTokenStorage: PlatformPreferences

    @RelaxedMockK
    lateinit var configurationStorage: ConfigurationStorage

    private lateinit var oAuthManager: OAuthManager

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        oAuthManager = OAuthManager(
            authTokenStorage,
            authApiLazyWrapper,
            platformTokenStorage,
            configurationStorage
        )
    }


    @Test
    fun `get Access Token returns AuthTokenStorage's accessToken`() {
        every { platformTokenStorage.rltToken } returns null
        every { authTokenStorage.accessToken } returns token
        val accessToken = oAuthManager.accessToken
        assertEquals(token, accessToken)
    }

    @Test
    fun `get Access Token returns AuthTokenStorage's accessToken while Platform accessToken is empty`() {
        every { platformTokenStorage.rltToken?.accessToken } returns EMPTY
        every { authTokenStorage.accessToken } returns token
        val accessToken = oAuthManager.accessToken
        assertEquals(token, accessToken)
    }

    @Test
    fun `get Access Token returns rltToken access Token`() {
        every { platformTokenStorage.rltToken } returns rltToken
        every { authTokenStorage.accessToken } returns EMPTY
        val accessToken = oAuthManager.accessToken
        assertEquals(rltToken.accessToken, accessToken)
    }

    @Test
    fun `ensure refresh token is not expired results success`() {
        every { platformTokenStorage.rltToken?.isTokenExpired } returns false
        oAuthManager.ensureActive()
        verify(exactly = 0) {
            authApiLazyWrapper.get().refreshToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId,
                refreshToken
            )
        }
    }

    @Test
    fun `ensure refresh token is expired and refresh token results success but the call fails`() {
        every { platformTokenStorage.rltToken } returns expiredRltToken
        every {
            authApiLazyWrapper.get().refreshToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId,
                refreshToken
            )
        } returns null
        oAuthManager.ensureActive()
        verify(exactly = 1) {
            authApiLazyWrapper.get().refreshToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId,
                refreshToken
            )

        }
    }

    @Test
    fun `ensure refresh token is expired and refresh token results success `() {
        every { platformTokenStorage.rltToken } returns expiredRltToken
        every {
            authApiLazyWrapper.get().refreshToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId,
                refreshToken
            )
        } returns rltToken
        oAuthManager.ensureActive()
        every { platformTokenStorage.rltToken } returns rltToken
        val tokenResult = platformTokenStorage.rltToken
        assertNotNull(tokenResult)
        assertEquals(rltToken, tokenResult)
        verify(exactly = 1) {
            authApiLazyWrapper.get().refreshToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId,
                refreshToken
            )
            platformTokenStorage.rltToken = rltToken
        }
    }


    @Test
    fun `ensure Access Token refresh token is not expired results success`() {
        every { platformTokenStorage.rltToken } returns null
        every { authTokenStorage.isTokenExpired } returns false
        oAuthManager.ensureActive()
        verify(exactly = 0) {
            authApiLazyWrapper.get().getAccessToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId
            )
        }
    }

    @Test
    fun `ensure Access Token refresh results success`() {
        every { platformTokenStorage.rltToken } returns null
        every { authTokenStorage.isTokenExpired } returns true
        every {
            authApiLazyWrapper.get().getAccessToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId
            )
        } returns accessTokenInfo
        oAuthManager.ensureActive()
        verify(exactly = 1) {
            authApiLazyWrapper.get().getAccessToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId
            )
            authTokenStorage.accessToken= accessTokenInfo.token
            authTokenStorage.expireAtMilliseconds= accessTokenInfo.expireAtMilliseconds
        }
    }
    @Test
    fun `ensure Access Token refresh results failure`() {
        every { platformTokenStorage.rltToken } returns null
        every { authTokenStorage.isTokenExpired } returns true
        every {
            authApiLazyWrapper.get().getAccessToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId
            )
        } returns null
        oAuthManager.ensureActive()
        verify(exactly = 1) {
            authApiLazyWrapper.get().getAccessToken(
                configurationStorage.clientSecret,
                configurationStorage.appCode.toClientId
            )
        }
    }
}