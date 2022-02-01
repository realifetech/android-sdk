package com.realifetech.sdk.identity.domain

import com.realifetech.fragment.AuthToken
import com.realifetech.sdk.identity.data.IdentityDataSource
import com.realifetech.sdk.identity.mocks.IdentityMocks
import com.realifetech.sdk.identity.mocks.IdentityMocks.DICTIONARY
import com.realifetech.sdk.identity.mocks.IdentityMocks.NONCE
import com.realifetech.sdk.identity.mocks.IdentityMocks.SALT
import com.realifetech.sdk.identity.mocks.IdentityMocks.authToken
import com.realifetech.sdk.identity.mocks.IdentityMocks.deviceId
import com.realifetech.sdk.identity.mocks.IdentityMocks.userInfo
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IdentityRepositoryTest {


    @RelaxedMockK
    lateinit var identityDataStore: IdentityDataSource
    lateinit var identityRepository: IdentityRepository
    lateinit var authenticateSlot: CapturingSlot<(error: Exception?, token: AuthToken?) -> Unit>
    lateinit var deviceSlot: CapturingSlot<(error: Exception?, deviceId: String?) -> Unit>
    private lateinit var nonceSlot: CapturingSlot<(error: Exception?, nonce: String?) -> Unit>

    @Before
    fun setup() {
        MockKAnnotations.init(this,relaxed = true)
        identityRepository = IdentityRepository(identityDataStore)
        nonceSlot = slot()
        authenticateSlot = slot()
        deviceSlot= slot()
    }

    @Test
    fun `attempt To Login return AuthToken successfully`() {
        every {
            identityDataStore.getDeviceId(capture(deviceSlot))}
            .answers { deviceSlot.captured.invoke(null, deviceId) }

        every { identityDataStore.generateNonce(capture(nonceSlot)) }
            .answers { nonceSlot.captured.invoke(null, NONCE) }
        every {
            identityDataStore.authenticateUserBySignedUserInfo(
                userInfo,
                capture(authenticateSlot)
            )
        }.answers { authenticateSlot.captured.invoke(null, authToken) }
        identityRepository.attemptToLogin(
            userInfo.email,
            userInfo.firstName.value,
            userInfo.lastName.value,
            SALT
        ) { authToken, error ->
            assertEquals(null, error)
            assertEquals(IdentityMocks.authToken, authToken)
        }
        verify {
            identityDataStore.authenticateUserBySignedUserInfo(
                userInfo,
                capture(authenticateSlot)
            )
        }

    }

    @Test
    fun `attempt To Login fails to generate Nonce`() {
        every {
            identityDataStore.getDeviceId(capture(deviceSlot))}
            .answers { deviceSlot.captured.invoke(null, deviceId) }

        every { identityDataStore.generateNonce(capture(nonceSlot)) }
            .answers { nonceSlot.captured.invoke(Exception(), null) }
        identityRepository.attemptToLogin(
            userInfo.email,
            userInfo.firstName.value,
            userInfo.lastName.value,
            SALT
        ) { authToken, error ->
            assert(error is Exception)
            assertEquals(null, authToken)
        }
    }

    @Test
    fun `attempt To Login return Exception `() {
        every {
            identityDataStore.getDeviceId(capture(deviceSlot))}
            .answers { deviceSlot.captured.invoke(null, deviceId) }

        every { identityDataStore.generateNonce(capture(nonceSlot)) }
            .answers { nonceSlot.captured.invoke(null, NONCE) }
        every {
            identityDataStore.authenticateUserBySignedUserInfo(
                userInfo,
                capture(authenticateSlot)
            )
        }
            .answers { authenticateSlot.captured.invoke(Exception(), null) }
        identityRepository.attemptToLogin(
            userInfo.email,
            userInfo.firstName.value,
            userInfo.lastName.value,
            SALT
        ) { authToken, error ->
            assert( error is Exception)
            assertEquals(null, authToken)
        }
        verify {
            identityDataStore.authenticateUserBySignedUserInfo(
                userInfo,
                capture(authenticateSlot)
            )
        }

    }

    @Test
    fun `get Encoded User Info`() {
        val encodedResult =
            identityRepository.getJsonObjectForData(
                userInfo.email,
                userInfo.firstName.value,
                userInfo.lastName.value,
                deviceId
            )
        Assert.assertEquals(DICTIONARY, encodedResult)
    }

}