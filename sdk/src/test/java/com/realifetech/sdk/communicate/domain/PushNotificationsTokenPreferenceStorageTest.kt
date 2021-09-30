package com.realifetech.sdk.communicate.domain

import android.content.Context
import androidx.core.content.edit
import com.realifetech.sdk.communicate.mocks.CommunicateMocks.token
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PushNotificationsTokenPreferenceStorageTest {

    @RelaxedMockK
    lateinit var context: Context
    private lateinit var notificationsPreferenceStorage: PushNotificationsTokenPreferenceStorage

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        notificationsPreferenceStorage = PushNotificationsTokenPreferenceStorage(context)
    }

    @Test
    fun `get Token results with data`() {
        every {
            notificationsPreferenceStorage.preferences.getString(TOKEN_KEY, "").orEmpty()
        }.returns(token)
        val result = notificationsPreferenceStorage.token
        assertEquals(token, result)
    }

    @Test
    fun `get Token results with empty`() {
        every {
            notificationsPreferenceStorage.preferences.getString(TOKEN_KEY, "")
        } returns null
        val result = notificationsPreferenceStorage.token
        assertEquals(EMPTY, result)
    }

    @Test
    fun setToken() {
        notificationsPreferenceStorage.token = token
        verify { notificationsPreferenceStorage.preferences.edit { putString(TOKEN_KEY, token) } }
    }

    @Test
    fun removeToken() {
        notificationsPreferenceStorage.removeToken()
        verify { notificationsPreferenceStorage.preferences.edit { remove(TOKEN_KEY) } }
    }

    companion object {
        private const val EMPTY = ""
        private const val TOKEN_KEY = "PushNotificationsToken"
    }
}