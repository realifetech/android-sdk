package com.realifetech.sdk.content

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.sdk.content.screen.domain.ScreenRepository
import com.realifetech.sdk.content.screen.mocks.ScreenMocks
import com.realifetech.sdk.content.webPage.domain.WebPageRepository
import com.realifetech.sdk.content.webPage.mocks.WebPageMocks
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.content.widgets.domain.WidgetsRepository
import com.realifetech.sdk.content.widgets.mocks.WidgetMocks
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ContentTest {

    @RelaxedMockK
    lateinit var webPageRepository: WebPageRepository

    @RelaxedMockK
    lateinit var widgetsRepository: WidgetsRepository

    @RelaxedMockK
    lateinit var screenRepository: ScreenRepository

    private lateinit var content: Content
    private lateinit var webPageSlot: CapturingSlot<(error: Exception?, response: FragmentWebPage?) -> Unit>
    private lateinit var screenSlot: CapturingSlot<(error: Exception?, response: List<Translation>?) -> Unit>
    private lateinit var widgetSlot: CapturingSlot<(error: Exception?, response: WidgetEdge?) -> Unit>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        content = Content(webPageRepository, widgetsRepository, screenRepository)
        webPageSlot = slot()
        screenSlot = slot()
        widgetSlot = slot()
    }

    @Test
    fun `get WebPage By Type returns data`() {
        //Given
        every {
            webPageRepository.getWebPageByType(
                WebPageMocks.webPageType,
                capture(webPageSlot)
            )
        }.answers { webPageSlot.captured.invoke(null, WebPageMocks.fragmentWebPage) }
        //When
        content.getWebPage(WebPageMocks.webPageType) { error, result ->
            //Then
            assertEquals(null, error)
            assertEquals(WebPageMocks.fragmentWebPage, result)
        }
    }

    @Test
    fun `get WebPage By Type returns error`() {
        //Given
        every {
            webPageRepository.getWebPageByType(
                WebPageMocks.webPageType,
                capture(webPageSlot)
            )
        }.answers { webPageSlot.captured.invoke(Exception(""), null) }
        //When
        content.getWebPage(WebPageMocks.webPageType) { error, result ->
            //Then
            assert(error is Exception)
            assertEquals(null, result)
        }
    }

    @Test
    fun `get Screen By Id results data`() {
        //Given
        every { screenRepository.getScreenById(ScreenMocks.screenId, capture(screenSlot)) }
            .answers { screenSlot.captured.invoke(null, ScreenMocks.resultTranslationById) }
        //When
        content.getScreenTitleById(ScreenMocks.screenId) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(ScreenMocks.resultTranslationById, response)

        }
    }

    @Test
    fun `get Screen By Id results error`() {
        //Given
        every { screenRepository.getScreenById(ScreenMocks.screenId, capture(screenSlot)) }
            .answers { screenSlot.captured.invoke(Exception(""), null) }
        //When
        content.getScreenTitleById(ScreenMocks.screenId) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }

    @Test
    fun `get Screen By Type results data`() {
        //Given
        every {
            screenRepository.getScreenByScreenType(
                ScreenMocks.screenType,
                capture(screenSlot)
            )
        }
            .answers { screenSlot.captured.invoke(null, ScreenMocks.resultTranslationByType) }
        //When
        content.getScreenTitleByScreenType(ScreenMocks.screenType) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(ScreenMocks.resultTranslationByType, response)

        }
    }

    @Test
    fun `get Screen By Type results error`() {
        //Given
        every {
            screenRepository.getScreenByScreenType(
                ScreenMocks.screenType,
                capture(screenSlot)
            )
        }
            .answers { screenSlot.captured.invoke(Exception(""), null) }
        //When
        content.getScreenTitleByScreenType(ScreenMocks.screenType) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }

    @Test
    fun `get Widgets By Id results data`() {
        //Given
        every {
            widgetsRepository.getWidgetsByScreenId(
                WidgetMocks.screenId,
                FFPMocks.PAGE_SIZE,
                FFPMocks.PAGE,
                capture(widgetSlot)
            )
        }
            .answers { widgetSlot.captured.invoke(null, WidgetMocks.widgetEdge) }
        //When
        content.getWidgetsByScreenId(
            WidgetMocks.screenId,
            FFPMocks.PAGE_SIZE,
            FFPMocks.PAGE,
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(WidgetMocks.widgetEdge, response)

        }
    }

    @Test
    fun `get Widgets By Id results error`() {
        //Given
        every {
            widgetsRepository.getWidgetsByScreenId(
                WidgetMocks.screenId, FFPMocks.PAGE_SIZE,
                FFPMocks.PAGE, capture(widgetSlot)
            )
        }
            .answers { widgetSlot.captured.invoke(Exception(""), null) }
        //When
        content.getWidgetsByScreenId(
            WidgetMocks.screenId, FFPMocks.PAGE_SIZE,
            FFPMocks.PAGE,
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }

    @Test
    fun `get Widgets By Type results data`() {
        //Given
        every {
            widgetsRepository.getWidgetsByScreenType(
                WidgetMocks.screenType, FFPMocks.PAGE_SIZE, FFPMocks.PAGE,
                capture(widgetSlot)
            )
        }
            .answers { widgetSlot.captured.invoke(null, WidgetMocks.widgetEdge) }
        //When
        content.getWidgetsByScreenType(
            WidgetMocks.screenType,
            FFPMocks.PAGE_SIZE, FFPMocks.PAGE
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(WidgetMocks.widgetEdge, response)

        }
    }

    @Test
    fun `get Widgets By Type results error`() {
        //Given
        every {
            widgetsRepository.getWidgetsByScreenType(
                WidgetMocks.screenType, FFPMocks.PAGE_SIZE, FFPMocks.PAGE,
                capture(widgetSlot)
            )
        }
            .answers { widgetSlot.captured.invoke(Exception(""), null) }
        //When
        content.getWidgetsByScreenType(
            WidgetMocks.screenType,
            FFPMocks.PAGE_SIZE, FFPMocks.PAGE
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }
}