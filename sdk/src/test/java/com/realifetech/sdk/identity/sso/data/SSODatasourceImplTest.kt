package com.realifetech.sdk.identity.sso.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyUserSSOQuery
import com.realifetech.GetMyUserSSOQuery.GetMyUserSSO
import com.realifetech.sdk.identity.sso.mocks.SSOMocks.expectedUser
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class SSODatasourceImplTest {

    @RelaxedMockK
    lateinit var apolloClient: ApolloClient
    private lateinit var ssoDatasource: SSODatasourceImpl
    private lateinit var userSSOData: Response<GetMyUserSSOQuery.Data>
    private lateinit var userSSOCapture: CapturingSlot<ApolloCall.Callback<GetMyUserSSOQuery.Data>>


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ssoDatasource = SSODatasourceImpl(apolloClient)
        userSSOCapture = slot()
        userSSOData = mockk()
    }


    @Test
    fun `get My User SSO returns data`() = runBlocking {
        // Given
        every {
            userSSOData.data?.getMyUserSSO
        } returns expectedUser
        getUserSSOSuccessAnswer()
        //When
        ssoDatasource.getMyUserSSO { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(expectedUser, response)
        }
    }

    @Test
    fun `get orders results with throwable`() = runBlocking {
        //Given
        every {
            userSSOData.data?.getMyUserSSO
        } throws ApolloHttpException(ArgumentMatchers.any())
        getUserSSOSuccessAnswer()
        // When
        ssoDatasource.getMyUserSSO { error, response ->
            //Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get orders results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.query(GetMyUserSSOQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(userSSOCapture))
        } answers { userSSOCapture.captured.onFailure(ApolloException("Error")) }
        // When
        ssoDatasource.getMyUserSSO { error, response ->
            // Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }
    }


    @Test
    fun `get orders results with null response`() = runBlocking {
        // Given
        val errorCallback = { error: Exception?, response: GetMyUserSSO? ->
            // Then
            Assert.assertSame(null, error)
            Assert.assertSame(null, response)
        }
        getUserSSOSuccessAnswer()
        every {
            userSSOData.data?.getMyUserSSO
        } returns null
        // When
        ssoDatasource.getMyUserSSO { error, response ->
            // Then
            errorCallback(error, response)
        }
        // Given
        every {
            userSSOData.data
        } returns null
        // When
        ssoDatasource.getMyUserSSO { error, response ->
            // Then
            errorCallback(error, response)
        }
    }

    private fun getUserSSOSuccessAnswer() {
        every {
            apolloClient.query(GetMyUserSSOQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
                .enqueue(capture(userSSOCapture))
        } answers {
            userSSOCapture.captured.onResponse(userSSOData)
        }
    }

}