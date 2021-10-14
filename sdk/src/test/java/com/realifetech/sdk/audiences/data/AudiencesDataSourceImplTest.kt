package com.realifetech.sdk.audiences.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.BelongsToAudienceWithExternalIdQuery
import com.realifetech.sdk.audiences.mocks.AudiencesMocks
import com.realifetech.sdk.audiences.mocks.AudiencesMocks.errors
import com.realifetech.sdk.audiences.mocks.AudiencesMocks.externalAudienceId
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class AudiencesDataSourceImplTest {
    @RelaxedMockK
    lateinit var apolloClient: ApolloClient
    lateinit var audiencesDataSource: AudiencesDataSourceImpl
    private lateinit var mockedResponse: Response<BelongsToAudienceWithExternalIdQuery.Data>
    private lateinit var captureSlot: CapturingSlot<ApolloCall.Callback<BelongsToAudienceWithExternalIdQuery.Data>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        audiencesDataSource = AudiencesDataSourceImpl(apolloClient)
        captureSlot = slot()
        mockedResponse = mockk()
    }

    @Test
    fun `belongs To Audience With ExternalId results success`() {
        every { mockedResponse.errors } returns null
        every { mockedResponse.data?.me?.device?.belongsToAudienceWithExternalId } returns true
        mockApiResponse()
        audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId) { error, result ->
            assertNull(error)
            assertEquals(true, result)
        }
    }

    @Test
    fun `belongs To Audience With ExternalId results error`() {
        every { mockedResponse.errors } returns AudiencesMocks.errors
        every { mockedResponse.data } returns null
        mockApiResponse()
        audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId) { error, result ->
            assertNotNull(error)
            assertEquals("something", error?.message)
            assertEquals(false, result)
        }
    }

    @Test
    fun `belongs To Audience With ExternalId results throwable`() {
        every { mockedResponse.errors } returns null
        every { mockedResponse.data } throws ApolloHttpException(any())
        mockApiResponse()
        audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId) { error, result ->
            assertNotNull(error)
            assertEquals(AudiencesMocks.expectedErrorMessage, error?.message)
            assertEquals(false, result)
        }
    }

    @Test
    fun `belongs To Audience With ExternalId results Failure`() {
        mockApiResponse(true)
        audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId) { error, result ->
            assertNotNull(error)
            assertEquals("message", error?.message)
            assertEquals(false, result)
        }
    }

    @Test
    fun `belongs To Audience With ExternalId results null`() {
        every { mockedResponse.errors } returns null
        val callback = { error: Error?, result: Boolean ->
            assertNull(error)
            assertEquals(false, result)
        }
        mockApiResponse()
        every { mockedResponse.data?.me?.device?.belongsToAudienceWithExternalId } returns null
        audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId, callback)
        every { mockedResponse.data?.me?.device } returns null
        audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId, callback)
        every { mockedResponse.data?.me } returns null
        audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId, callback)
        every { mockedResponse.data } returns null
        audiencesDataSource.belongsToAudienceWithExternalId(externalAudienceId, callback)
    }

    private fun mockApiResponse(failing: Boolean = false) {
        every {
            apolloClient.query(BelongsToAudienceWithExternalIdQuery(externalAudienceId)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(captureSlot))
        }.answers {
            if (failing) {
                captureSlot.captured.onFailure(ApolloException("message"))
            } else {
                captureSlot.captured.onResponse(mockedResponse)
            }
        }
    }
}