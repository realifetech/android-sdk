package com.realifetech.sdk.content.screen.domain

import com.realifetech.sdk.content.screen.data.datasource.ScreensDataSource
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.resultTranslationById
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.resultTranslationByType
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.screenId
import com.realifetech.sdk.content.screen.mocks.ScreenMocks.screenType
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ScreenRepositoryTest {

    @RelaxedMockK
    lateinit var screensDataSource: ScreensDataSource
    private lateinit var screenRepository: ScreenRepository
    private lateinit var screenSlot: CapturingSlot<(error: Exception?, response: List<Translation>?) -> Unit>

    @Before

    fun setUp() {
        MockKAnnotations.init(this)
        screenRepository = ScreenRepository(screensDataSource)
        screenSlot = slot()
    }

    @Test
    fun `get Screen By Id results data`() {
        //Given
        every { screensDataSource.getScreenById(screenId, capture(screenSlot)) }
            .answers { screenSlot.captured.invoke(null, resultTranslationById) }
        //When
        screenRepository.getScreenById(screenId) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(resultTranslationById, response)

        }
    }

    @Test
    fun `get Screen By Id results error`() {
        //Given
        every { screensDataSource.getScreenById(screenId, capture(screenSlot)) }
            .answers { screenSlot.captured.invoke(Exception(""), null) }
        //When
        screenRepository.getScreenById(screenId) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }

    @Test
    fun `get Screen By Type results data`() {
        //Given
        every { screensDataSource.getScreenByScreenType(screenType, capture(screenSlot)) }
            .answers { screenSlot.captured.invoke(null, resultTranslationByType) }
        //When
        screenRepository.getScreenByScreenType(screenType) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(resultTranslationByType, response)

        }
    }

    @Test
    fun `get Screen By Type results error`() {
        //Given
        every { screensDataSource.getScreenByScreenType(screenType, capture(screenSlot)) }
            .answers { screenSlot.captured.invoke(Exception(""), null) }
        //When
        screenRepository.getScreenByScreenType(screenType) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)

        }
    }
}