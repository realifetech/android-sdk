package com.realifetech.sdk.identity.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.AuthenticateUserBySignedUserInfoMutation
import com.realifetech.GenerateNonceMutation
import com.realifetech.fragment.AuthToken
import com.realifetech.sdk.identity.mocks.IdentityMocks
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class IdentityDataSourceImplTest {

    @RelaxedMockK
    private lateinit var apolloClient: ApolloClient
    private lateinit var identityDataSource: IdentityDataSourceImpl
    private lateinit var nonceData: Response<GenerateNonceMutation.Data>
    private lateinit var authenticateInfoData: Response<AuthenticateUserBySignedUserInfoMutation.Data>
    private lateinit var nonceSlot: CapturingSlot<ApolloCall.Callback<GenerateNonceMutation.Data>>
    private lateinit var authenticateInfoSlot: CapturingSlot<ApolloCall.Callback<AuthenticateUserBySignedUserInfoMutation.Data>>
    private lateinit var mutation: GenerateNonceMutation

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        initMockedFields()
    }

    private fun initMockedFields() {
        identityDataSource = IdentityDataSourceImpl(apolloClient)
        nonceData = mockk()
        mutation = mockk()
        authenticateInfoData = mockk()
        nonceSlot = slot()
        authenticateInfoSlot = slot()
    }

    @Test
    fun `generate Nonce return data`() = runBlocking {
        every { nonceData.data?.generateNonce } returns GenerateNonceMutation.GenerateNonce(
            "",
            IdentityMocks.NONCE
        )
        generateNonceSuccessfully()

        identityDataSource.generateNonce { error, response ->
            assertEquals(null, error)
            assertEquals(IdentityMocks.NONCE, response)
        }
    }


    @Test
    fun `generate Nonce throws exception`() {
        every { nonceData.data?.generateNonce } throws ApolloHttpException(null)
        generateNonceSuccessfully()
        identityDataSource.generateNonce { error, response ->
            assert(error is ApolloHttpException)
            assertEquals(null, response)

        }
    }

    @Test
    fun `generate Nonce return failure`() {
        every {
            apolloClient.mutate(GenerateNonceMutation())
                .enqueue(capture(nonceSlot))
        } answers { nonceSlot.captured.onFailure(ApolloException("")) }

        identityDataSource.generateNonce { error, response ->
            assert(error is ApolloException)
            assertEquals(null, response)

        }

    }

    @Test
    fun `generate Nonce return exception when null`() {
    }

    @Test
    fun `authenticate User By SignedUserInfo return data`() {
        every {
            authenticateInfoData.data?.authenticateUserBySignedUserInfo?.fragments?.authToken
        } returns IdentityMocks.authToken
        authenticateUserSuccessfully()

        identityDataSource.authenticateUserBySignedUserInfo(IdentityMocks.userInfo) { error, response ->
            assertEquals(null, error)
            assertEquals(IdentityMocks.authToken, response)
        }

    }

    @Test
    fun `authenticate User By SignedUserInfo throws exception`() {
        every {
            authenticateInfoData.data?.authenticateUserBySignedUserInfo?.fragments?.authToken
        } throws ApolloHttpException(null)
        authenticateUserSuccessfully()
        identityDataSource.authenticateUserBySignedUserInfo(IdentityMocks.userInfo) { error, response ->
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `authenticate User By SignedUserInfo return failure`() {
        every {
            apolloClient.mutate(AuthenticateUserBySignedUserInfoMutation(IdentityMocks.userInfo))
                .enqueue(capture(authenticateInfoSlot))
        } answers { authenticateInfoSlot.captured.onFailure(ApolloException("")) }

        identityDataSource.authenticateUserBySignedUserInfo(IdentityMocks.userInfo) { error, response ->
            assert(error is ApolloException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `authenticate User By SignedUserInfo return exception when null`() {
        authenticateUserSuccessfully()
        val callback = { error: Exception?, response: AuthToken? ->
            assert(error is Exception)
            assertEquals(null, response)

        }
        every {
            authenticateInfoData.data?.authenticateUserBySignedUserInfo?.fragments
        } returns null

        identityDataSource.authenticateUserBySignedUserInfo(IdentityMocks.userInfo) { error, response ->
            callback.invoke(error, response)
        }
        every {
            authenticateInfoData.data?.authenticateUserBySignedUserInfo
        } returns null

        identityDataSource.authenticateUserBySignedUserInfo(IdentityMocks.userInfo) { error, response ->
            callback.invoke(error, response)
        }
        every {
            authenticateInfoData.data
        } returns null

        identityDataSource.authenticateUserBySignedUserInfo(IdentityMocks.userInfo) { error, response ->
            callback.invoke(error, response)
        }

    }

    private fun generateNonceSuccessfully() {
        every {
            apolloClient.mutate(GenerateNonceMutation()).enqueue(capture(nonceSlot))
        } answers { nonceSlot.captured.onResponse(nonceData) }
    }


    private fun authenticateUserSuccessfully() {
        every {
            apolloClient.mutate(AuthenticateUserBySignedUserInfoMutation(IdentityMocks.userInfo))
                .enqueue(capture(authenticateInfoSlot))
        } answers { authenticateInfoSlot.captured.onResponse(authenticateInfoData) }
    }
}