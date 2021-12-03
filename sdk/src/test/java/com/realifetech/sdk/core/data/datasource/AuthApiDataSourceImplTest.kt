package com.realifetech.sdk.core.data.datasource

import com.realifetech.sdk.core.data.model.auth.OAuthTokenResponse
import com.realifetech.sdk.core.mocks.AuthMocks.accessTokenInfo
import com.realifetech.sdk.core.mocks.AuthMocks.refreshToken
import com.realifetech.sdk.core.mocks.AuthMocks.resultExpiryTime
import com.realifetech.sdk.core.mocks.AuthMocks.rltTokenResult
import com.realifetech.sdk.core.mocks.AuthMocks.tokenBody
import com.realifetech.sdk.core.mocks.NetworkMocks.expireAtMilliSeconds
import com.realifetech.sdk.core.mocks.NetworkMocks.rltToken
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.DeviceCalendar
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.*

class AuthApiDataSourceImplTest {


    @RelaxedMockK
    lateinit var authorizationApiNetwork: RealifetechApiV3Service

    @RelaxedMockK
    lateinit var deviceCalendar: DeviceCalendar
    private lateinit var authApiDataSourceImpl: AuthApiDataSourceImpl
    private lateinit var response: Response<OAuthTokenResponse>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authApiDataSourceImpl = AuthApiDataSourceImpl(authorizationApiNetwork, deviceCalendar)
        response = mockk()
    }

    @Test
    fun `get Access Token returns access token body`() {
        every {
            authorizationApiNetwork.getAuthToken(any()).execute()
        } returns response
        every { response.isSuccessful } returns true
        every { response.body() } returns rltToken
        every { deviceCalendar.currentTime } returns expireAtMilliSeconds
        val result = authApiDataSourceImpl.getAccessToken(
            tokenBody.clientSecret,
            tokenBody.clientId
        )
        assertNotNull(result)
        assertEquals(accessTokenInfo, result)

    }

    @Test
    fun `get Access Token returns null`() {
        every {
            authorizationApiNetwork.getAuthToken(any()).execute()
        } returns response
        every { response.isSuccessful } returns true
        every { response.body() } returns null
        every { deviceCalendar.currentTime } returns expireAtMilliSeconds
        val result = authApiDataSourceImpl.getAccessToken(
            tokenBody.clientSecret,
            tokenBody.clientId
        )
        assertNull(result)
    }

    @Test
    fun `refresh Token returns success response`() {
        every {
            authorizationApiNetwork.refreshAuthToken(any()).execute()
        } returns response
        every { response.isSuccessful } returns true
        every { response.body() } returns rltToken
        every { deviceCalendar.time } returns Date(resultExpiryTime)
        val result = authApiDataSourceImpl.refreshToken(
            tokenBody.clientSecret,
            tokenBody.clientId,
            refreshToken
        )
        assertNotNull(result)
        assertEquals(rltTokenResult, result)
        assertEquals(rltTokenResult.refreshExpiry, result?.refreshExpiry)
        assertEquals(false, result?.isTokenExpired)

    }

    @Test
    fun `refresh Token returns null`() {
        every {
            authorizationApiNetwork.refreshAuthToken(any()).execute()
        } returns response
        every { response.isSuccessful } returns true
        every { response.body() } returns null
        every { deviceCalendar.currentTime } returns expireAtMilliSeconds
        val result = authApiDataSourceImpl.refreshToken(
            tokenBody.clientSecret,
            tokenBody.clientId,
            refreshToken
        )
        assertNull(result)

    }
}