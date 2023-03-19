package com.realifetech.sdk.campaignautomation.data.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.sdk.campaignautomation.domain.CAMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString

@ExperimentalCoroutinesApi
class CampaignAutomationDataSourceImplementationTest {

    @RelaxedMockK
    private lateinit var apolloClient: ApolloClient
    private lateinit var caSlot: CapturingSlot<ApolloCall.Callback<GetContentByExternalIdQuery.Data>>
    private lateinit var getCaData: Response<GetContentByExternalIdQuery.Data>
    private lateinit var caDataSource: CampaignAutomationDataSourceImplementation

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        initMockedFields()
    }

    private fun initMockedFields() {
        caDataSource = CampaignAutomationDataSourceImplementation(apolloClient)
        getCaData = mockk()
        caSlot = CapturingSlot()
    }

    @Test
    fun `GetContentByExternalIdQuery returns Data`() = runBlockingTest {
        // Given
        every {
            getCaData.data?.getContentByExternalId
        } returns CAMocks.mock
        getContentByExternalIdQuerySuccessAnswer("banana")
        // When
        caDataSource.getContentByExternalId("banana") {
            error, response ->
            // Then
            Assert.assertEquals(CAMocks.mock, response)
            Assert.assertEquals(null, error)
        }
    }

    @Test
    fun `GetContentByExternalIdQuery returns Exception`() = runBlockingTest {
        // Given
        every {
            getCaData.data?.getContentByExternalId
        } throws ApolloHttpException(any())

        getContentByExternalIdQuerySuccessAnswer("banana")
        // When
        caDataSource.getContentByExternalId("banana") { error, response ->
            // Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }
    }


    @Test
    fun `GetContentByExternalIdQuery returns Failure`() = runBlockingTest {
        // Given
        every {
            apolloClient.query(GetContentByExternalIdQuery(anyString())).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                .build()
                .enqueue(capture(caSlot))
        } answers {
            caSlot.captured.onFailure(ApolloException("Error"))
        }
        // When
        caDataSource.getContentByExternalId("banana") { error, response ->
            // Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }
    }

    private fun getContentByExternalIdQuerySuccessAnswer(value: String) {
        every {
            apolloClient.query(GetContentByExternalIdQuery(value)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
                .enqueue(capture(caSlot))
        } answers {
            caSlot.captured.onResponse(getCaData)
        }
    }

}