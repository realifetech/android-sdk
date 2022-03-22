package com.realifetech.sdk.identity.sso.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetUserAliasesQuery
import com.realifetech.fragment.FragmentUserAlias
import com.realifetech.sdk.identity.sso.mocks.SSOMocks.expectedUserAlias
import com.realifetech.sdk.identity.sso.mocks.SSOMocks.result
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
    private lateinit var userSSOData: Response<GetUserAliasesQuery.Data>
    private lateinit var userSSOCapture: CapturingSlot<ApolloCall.Callback<GetUserAliasesQuery.Data>>


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ssoDatasource = SSODatasourceImpl(apolloClient)
        userSSOCapture = slot()
        userSSOData = mockk()
    }


    @Test
    fun `get User Alias returns data`() = runBlocking {
        // Given
        every {
            userSSOData.data
        } returns result
        getUserAliasSuccessAnswer()
        //When
        ssoDatasource.getUserAlias { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(expectedUserAlias, response)
        }
    }

    @Test
    fun `get user alias results with throwable`() = runBlocking {
        //Given
        every {
            userSSOData.data?.me?.user?.userAliases?.firstOrNull()?.fragments?.fragmentUserAlias
        } throws ApolloHttpException(ArgumentMatchers.any())
        getUserAliasSuccessAnswer()
        // When
        ssoDatasource.getUserAlias { error, response ->
            //Then
            assert(error is ApolloHttpException)
            Assert.assertEquals(null, response)
        }
    }

    @Test
    fun `get user alias results with failure`() = runBlocking {
        //Given
        every {
            apolloClient.query(GetUserAliasesQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY).build()
                .enqueue(capture(userSSOCapture))
        } answers { userSSOCapture.captured.onFailure(ApolloException("Error")) }
        // When
        ssoDatasource.getUserAlias { error, response ->
            // Then
            assert(error is ApolloException)
            Assert.assertEquals(null, response)
        }
    }


    @Test
    fun `get user alias results with null response`() = runBlocking {
        // Given
        val errorCallback = { error: Exception?, response: FragmentUserAlias? ->
            // Then
            Assert.assertSame(null, error)
            Assert.assertSame(null, response)
        }
        getUserAliasSuccessAnswer()
        every {
            userSSOData.data?.me?.user?.userAliases?.firstOrNull()?.fragments?.fragmentUserAlias
        } returns null
        // When
        ssoDatasource.getUserAlias { error, response ->
            // Then
            errorCallback(error, response)
        }
        // Given
        every {
            userSSOData.data?.me?.user?.userAliases?.firstOrNull()?.fragments
        } returns null
        // When
        ssoDatasource.getUserAlias { error, response ->
            // Then
            errorCallback(error, response)
        }
        // Given
        every {
            userSSOData.data?.me?.user?.userAliases?.firstOrNull()
        } returns null
        // When
        ssoDatasource.getUserAlias { error, response ->
            // Then
            errorCallback(error, response)
        }
        // Given
        every {
            userSSOData.data?.me?.user?.userAliases
        } returns null
        // When
        ssoDatasource.getUserAlias { error, response ->
            // Then
            errorCallback(error, response)
        }
        // Given
        every {
            userSSOData.data?.me?.user
        } returns null
        // When
        ssoDatasource.getUserAlias { error, response ->
            // Then
            errorCallback(error, response)
        }
        // Given
        every {
            userSSOData.data?.me
        } returns null
        // When
        ssoDatasource.getUserAlias { error, response ->
            // Then
            errorCallback(error, response)
        }
        // Given
        every {
            userSSOData.data
        } returns null
        // When
        ssoDatasource.getUserAlias { error, response ->
            // Then
            errorCallback(error, response)
        }
    }

    private fun getUserAliasSuccessAnswer() {
        every {
            apolloClient.query(GetUserAliasesQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
                .enqueue(capture(userSSOCapture))
        } answers {
            userSSOCapture.captured.onResponse(userSSOData)
        }
    }

}