package com.realifetech.sdk.content.webPage.domain

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sdk.content.webPage.data.WebPageDataSource
import com.realifetech.sdk.content.webPage.mocks.WebPageMocks.fragmentWebPage
import com.realifetech.sdk.content.webPage.mocks.WebPageMocks.webPageType
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WebPageRepositoryTest {

    @RelaxedMockK
    lateinit var webPageDataSource: WebPageDataSource
    private lateinit var webPageRepository: WebPageRepository
    private lateinit var webPageSlot: CapturingSlot<(error: Exception?, response: FragmentWebPage?) -> Unit>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        webPageRepository = WebPageRepository(webPageDataSource)
        webPageSlot = slot()
    }

    @Test
    fun `get WebPage By Type returns data`() {
        //Given
        every {
            webPageDataSource.getWebPageByType(
                webPageType,
                capture(webPageSlot)
            )
        }.answers { webPageSlot.captured.invoke(null, fragmentWebPage) }
        //When
        webPageRepository.getWebPageByType(webPageType) { error, result ->
            //Then
            assertEquals(null, error)
            assertEquals(fragmentWebPage, result)
        }
    }

    @Test
    fun `get WebPage By Type returns error`() {
        //Given
        every {
            webPageDataSource.getWebPageByType(
                webPageType,
                capture(webPageSlot)
            )
        }.answers { webPageSlot.captured.invoke(Exception(""), null) }
        //When
        webPageRepository.getWebPageByType(webPageType) { error, result ->
            //Then
            assert(error is Exception)
            assertEquals(null, result)
        }
    }
}