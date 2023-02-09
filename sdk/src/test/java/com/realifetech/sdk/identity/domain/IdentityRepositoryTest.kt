package com.realifetech.sdk.identity.domain

import com.apollographql.apollo.exception.ApolloException
import com.realifetech.fragment.AuthToken
import com.realifetech.sdk.identity.data.IdentityDataSource
import com.realifetech.sdk.identity.mocks.IdentityMocks
import com.realifetech.sdk.identity.mocks.IdentityMocks.DICTIONARY
import com.realifetech.sdk.identity.mocks.IdentityMocks.NONCE
import com.realifetech.sdk.identity.mocks.IdentityMocks.SALT
import com.realifetech.sdk.identity.mocks.IdentityMocks.authToken
import com.realifetech.sdk.identity.mocks.IdentityMocks.authUrl
import com.realifetech.sdk.identity.mocks.IdentityMocks.deviceId
import com.realifetech.sdk.identity.mocks.IdentityMocks.provider
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
    lateinit var getSsoSlot: CapturingSlot<(error: Exception?, url:String?) -> Unit>
    lateinit var deviceSlot: CapturingSlot<(error: Exception?, deviceId: String?) -> Unit>
    lateinit var deleteMyAccountSlot: CapturingSlot<(error: Exception?, success: Boolean?) -> Unit>
    private lateinit var nonceSlot: CapturingSlot<(error: Exception?, nonce: String?) -> Unit>

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        identityRepository = IdentityRepository(identityDataStore)
        nonceSlot = slot()
        getSsoSlot= slot()
        deleteMyAccountSlot = slot()
        authenticateSlot = slot()
        deviceSlot = slot()
    }

    @Test
    fun `attempt To Login return AuthToken successfully`() {
        every {
            identityDataStore.getDeviceId(capture(deviceSlot))
        }
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
            identityDataStore.getDeviceId(capture(deviceSlot))
        }
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
            identityDataStore.getDeviceId(capture(deviceSlot))
        }
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
            assert(error is Exception)
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
    fun `attempt to delete My Account return success`() {
        every { identityRepository.deleteMyAccount(capture(deleteMyAccountSlot)) } answers {
            deleteMyAccountSlot.captured.invoke(null, true)
        }
        identityRepository.deleteMyAccount { error, success ->
            Assert.assertEquals(true, success)
            Assert.assertEquals(null, error)
        }
    }

    @Test
    fun `attempt to get sso return url`() {
        every { identityDataStore.getSSO(provider,capture(getSsoSlot)) } answers {
            getSsoSlot.captured.invoke(null, authUrl)
        }
        identityRepository.getSSO(provider) { error, url ->
            Assert.assertEquals(authUrl, url)
            Assert.assertEquals(null, error)
        }
    }
    @Test
    fun `attempt to get sso return error`() {
        every { identityDataStore.getSSO(provider,capture(getSsoSlot)) } answers {
            getSsoSlot.captured.invoke(ApolloException(""), null)
        }
        identityRepository.getSSO(provider) { error, url ->
            Assert.assertEquals(null, url)
            assert(error is ApolloException)
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