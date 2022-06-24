package com.realifetech.sdk.communicate.domain

import com.realifetech.sdk.communicate.mocks.CommunicateMocks.token
import com.realifetech.sdk.util.Constants.EMPTY
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PushNotificationsTokenStorageTest {

    @RelaxedMockK
    lateinit var pushNotificationStorage: PushNotificationStorage

    private lateinit var notificationsTokenStorage: PushNotificationsTokenStorage

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        notificationsTokenStorage = PushNotificationsTokenStorage(pushNotificationStorage)
    }

    @Test
    fun getPendingToken() {
        every { pushNotificationStorage.token } returns token
        val result = notificationsTokenStorage.pendingToken
        assertEquals(token, result)
    }

    @Test
    fun setPendingToken() {
        notificationsTokenStorage.pendingToken = token
        verify { pushNotificationStorage.token = token }
    }

    @Test
    fun getHasPendingToken() {
        every { pushNotificationStorage.token } returns EMPTY
        var result = notificationsTokenStorage.hasPendingToken
        assertEquals(false, result)
        every { pushNotificationStorage.token } returns token
        result = notificationsTokenStorage.hasPendingToken
        assertEquals(true, result)
    }

    @Test
    fun removePendingToken() {
        notificationsTokenStorage.removePendingToken()
        verify { pushNotificationStorage.removeToken() }
    }
}