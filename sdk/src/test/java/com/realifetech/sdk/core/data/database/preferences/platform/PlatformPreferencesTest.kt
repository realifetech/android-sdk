package com.realifetech.sdk.core.data.database.preferences.platform

import android.content.Context
import com.realifetech.sdk.core.mocks.NetworkMocks.rltToken
import com.realifetech.sdk.core.mocks.NetworkMocks.tokenResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PlatformPreferencesTest {

    @RelaxedMockK
    lateinit var context: Context
    private lateinit var platformStorage: PlatformPreferences

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        platformStorage = PlatformPreferences(context)
    }

    @Test
    fun getPreferencesStorageName() {
        val result = platformStorage.preferencesStorageName
        assertEquals(REALIFETECH_PREFERENCES, result)
    }

    @Test
    fun `get Rlt Token returns token object`() {
        every {
            platformStorage.preferences.getString(OAUTH_TOKEN, "")
        }.returns(tokenResponse)
        val result = platformStorage.rltToken
        assertEquals(rltToken, result)
    }

    @Test
    fun `get Rlt Token returns null`() {
        every {
            platformStorage.preferences.getString(OAUTH_TOKEN, "")
        }.returns("Batata")
        val result = platformStorage.rltToken
        assertEquals(null, result)

    }

    @Test
    fun `set valid Rlt Token`() {
        platformStorage.rltToken = rltToken
        verify { platformStorage.preferences.edit().putString(OAUTH_TOKEN, tokenResponse).apply() }
    }

    @Test
    fun `set null Rlt Token`() {
        platformStorage.rltToken = null
        verify(exactly = 0) { platformStorage.preferences.edit().putString(OAUTH_TOKEN, tokenResponse).apply()}
    }


    companion object {
        private const val REALIFETECH_PREFERENCES = "LiveStyledPreferences"
        private const val OAUTH_TOKEN = "oauth-token"

    }
}