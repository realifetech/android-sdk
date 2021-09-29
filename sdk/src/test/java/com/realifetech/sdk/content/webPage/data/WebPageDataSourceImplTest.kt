package com.realifetech.sdk.content.webPage.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetWebPageByTypeQuery
import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sdk.content.webPage.mocks.WebPageMocks
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any

@ExperimentalCoroutinesApi
class WebPageDataSourceImplTest {

    @RelaxedMockK
    lateinit var apolloClient: ApolloClient
    private lateinit var webPageDataSource: WebPageDataSourceImpl

    private lateinit var webPageData: Response<GetWebPageByTypeQuery.Data>
    private lateinit var webPageSlot: CapturingSlot<ApolloCall.Callback<GetWebPageByTypeQuery.Data>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webPageDataSource = WebPageDataSourceImpl(apolloClient)
        initMockedFields()
    }

    private fun initMockedFields() {
        webPageData = mockk()
        webPageSlot = slot()
    }

    @Test
    fun `get Web Page By Type returns data`() = runBlockingTest {
        // Given
        every { webPageData.data?.getWebPageByType?.fragments?.fragmentWebPage } returns WebPageMocks.fragmentWebPage
        //When
        getWebPageByTypeCall()
        //Then
        webPageDataSource.getWebPageByType(WebPageMocks.webPageType) { error, response ->
            assertEquals(null, error)
            assertEquals(WebPageMocks.fragmentWebPage, response)
        }
    }

    @Test
    fun `get Web Page By Type throws exception`() = runBlockingTest {
        // Given
        every { webPageData.data?.getWebPageByType?.fragments?.fragmentWebPage } throws ApolloHttpException(
            any()
        )
        getWebPageByTypeCall()
        //When
        webPageDataSource.getWebPageByType(WebPageMocks.webPageType) { error, response ->
            //Then
            assert(error is ApolloHttpException)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Web Page By Type returns failure`() = runBlockingTest {
        // Given
        getWebPageByTypeCall(shouldFail = true)
        //When
        webPageDataSource.getWebPageByType(WebPageMocks.webPageType) { error, response ->
            //Then
            assert(error is ApolloException)
            assertEquals(null, response)
        }

    }

    private fun getWebPageByTypeCall(shouldFail: Boolean = false) {
        every {
            apolloClient.query(GetWebPageByTypeQuery(WebPageMocks.webPageType)).toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK).build()
                .enqueue(capture(webPageSlot))
        } answers {
            if (shouldFail) {
                webPageSlot.captured.onFailure(ApolloException(""))
            } else {
                webPageSlot.captured.onResponse(webPageData)
            }
        }
    }

        @Test
        fun `get Web Page By Type returns null`() = runBlockingTest {

            // Given
            val callback = { error: Exception?, result: FragmentWebPage? ->
                assertEquals(null, error)
                assertEquals(null, result)
            }
            getWebPageByTypeCall()

            every { webPageData.data?.getWebPageByType?.fragments?.fragmentWebPage } returns null
            //When
            webPageDataSource.getWebPageByType(WebPageMocks.webPageType) { error, response ->
                //Then
                callback.invoke(error, response)
            }

            every { webPageData.data?.getWebPageByType?.fragments } returns null
            //When
            webPageDataSource.getWebPageByType(WebPageMocks.webPageType) { error, response ->
                //Then
                callback.invoke(error, response)
            }

            every { webPageData.data?.getWebPageByType } returns null
            //When
            webPageDataSource.getWebPageByType(WebPageMocks.webPageType) { error, response ->
                //Then
                callback.invoke(error, response)
            }

            every { webPageData.data } returns null
            //When
            webPageDataSource.getWebPageByType(WebPageMocks.webPageType) { error, response ->
                //Then
                callback.invoke(error, response)
            }
        }
    }