package com.realifetech.sdk.core.data.database.preferences.auth

import android.content.Context
import androidx.core.content.edit
import com.realifetech.sdk.core.data.model.shared.translation.EMPTY
import com.realifetech.sdk.core.mocks.NetworkMocks.accessToken
import com.realifetech.sdk.core.mocks.NetworkMocks.expireAtMilliSeconds
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AuthTokenStorageTest {
    @RelaxedMockK
    lateinit var context: Context
    private lateinit var authTokenStorage: AuthTokenStorage

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authTokenStorage = AuthTokenStorage(context)
    }


    @Test
    fun `get Access Token returns token`() {
        every {
            authTokenStorage.preferences.getString(ACCESS_TOKEN_KEY, "").orEmpty()
        }.returns(accessToken)
        val result = authTokenStorage.accessToken
        assertEquals(accessToken, result)
    }

    @Test
    fun `get Access Token returns empty`() {
        every {
            authTokenStorage.preferences.getString(ACCESS_TOKEN_KEY, "")
        }.returns(null)
        val result = authTokenStorage.accessToken
        assertEquals(EMPTY, result)
    }

    @Test
    fun setAccessToken() {
        authTokenStorage.accessToken = accessToken
        verify { authTokenStorage.preferences.edit { putString(ACCESS_TOKEN_KEY, accessToken) } }

    }

    @Test
    fun getExpireAtMilliseconds() {
        every {
            authTokenStorage.preferences.getLong(EXPIRE_TOKEN_TIME_KEY, 0)
        }.returns(expireAtMilliSeconds)
        val result = authTokenStorage.expireAtMilliseconds
        assertEquals(expireAtMilliSeconds, result)
    }

    @Test
    fun setExpireAtMilliseconds() {
        authTokenStorage.expireAtMilliseconds = expireAtMilliSeconds
        verify {
            authTokenStorage.preferences.edit {
                putLong(
                    EXPIRE_TOKEN_TIME_KEY,
                    expireAtMilliSeconds
                )
            }
        }

    }

    @Test
    fun `is Token Expired returns false`() {
        every {
            authTokenStorage.preferences.getLong(EXPIRE_TOKEN_TIME_KEY, 0)
        }.returns(expireAtMilliSeconds)
        authTokenStorage.expireAtMilliseconds = expireAtMilliSeconds
        val result = authTokenStorage.isTokenExpired
        assertEquals(false, result)
    }

    @Test
    fun `is Token Expired returns true`() {

        authTokenStorage.expireAtMilliseconds = expireAtMilliSeconds
        val result = authTokenStorage.isTokenExpired
        assertEquals(true, result)
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "AccessTokenOAuth"
        private const val EXPIRE_TOKEN_TIME_KEY = "ExpireTokenOAuth"
    }
}