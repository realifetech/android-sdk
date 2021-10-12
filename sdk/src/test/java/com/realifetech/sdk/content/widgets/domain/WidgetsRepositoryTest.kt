package com.realifetech.sdk.content.widgets.domain


import com.realifetech.sdk.content.widgets.data.datasource.WidgetsDataSource
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.content.widgets.mocks.WidgetMocks
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.PAGE
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks.PAGE_SIZE
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WidgetsRepositoryTest {


    @RelaxedMockK
    lateinit var widgetsDataSource: WidgetsDataSource
    private lateinit var widgetRepository: WidgetsRepository
    private lateinit var widgetSlot: CapturingSlot<(error: Exception?, response: WidgetEdge?) -> Unit>

    @Before

    fun setUp() {
        MockKAnnotations.init(this)
        widgetRepository = WidgetsRepository(widgetsDataSource)
        widgetSlot = slot()
    }


    @Test
    fun `get Widgets By Id results data`() {
        //Given
        every {
            widgetsDataSource.getWidgetsByScreenId(
                WidgetMocks.screenId,
                PAGE_SIZE,
                PAGE,
                capture(widgetSlot)
            )
        }
            .answers { widgetSlot.captured.invoke(null, WidgetMocks.widgetEdge) }
        //When
        widgetRepository.getWidgetsByScreenId(
            WidgetMocks.screenId,
            PAGE_SIZE,
            PAGE,
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
            widgetsDataSource.getWidgetsByScreenId(
                WidgetMocks.screenId, PAGE_SIZE,
                PAGE, capture(widgetSlot)
            )
        }
            .answers { widgetSlot.captured.invoke(Exception(""), null) }
        //When
        widgetRepository.getWidgetsByScreenId(
            WidgetMocks.screenId, PAGE_SIZE,
            PAGE,
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
            widgetsDataSource.getWidgetsByScreenType(
                WidgetMocks.screenType, PAGE_SIZE, PAGE,
                capture(widgetSlot)
            )
        }
            .answers { widgetSlot.captured.invoke(null, WidgetMocks.widgetEdge) }
        //When
        widgetRepository.getWidgetsByScreenType(
            WidgetMocks.screenType,
            PAGE_SIZE, PAGE
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
            widgetsDataSource.getWidgetsByScreenType(
                WidgetMocks.screenType, PAGE_SIZE, PAGE,
                capture(widgetSlot)
            )
        }
            .answers { widgetSlot.captured.invoke(Exception(""), null) }
        //When
        widgetRepository.getWidgetsByScreenType(
            WidgetMocks.screenType,
            PAGE_SIZE, PAGE
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }
}