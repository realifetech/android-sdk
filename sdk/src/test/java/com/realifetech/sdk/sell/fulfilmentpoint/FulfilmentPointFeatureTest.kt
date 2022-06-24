package com.realifetech.sdk.sell.fulfilmentpoint

import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.fulfilmentpoint.domain.FulfilmentPointRepository
import com.realifetech.sdk.sell.fulfilmentpoint.mocks.FFPMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FulfilmentPointFeatureTest {

    @RelaxedMockK
    private lateinit var fulfilmentPointRepository: FulfilmentPointRepository
    private lateinit var ffpSlot: CapturingSlot<(error: Exception?, fulfilmentPoint: FulfilmentPoint?) -> Unit>
    private lateinit var paginatedFFPSlot: CapturingSlot<(error: Exception?, response: PaginatedObject<FulfilmentPoint?>?) -> Unit>
    private lateinit var ffpCategorySlot: CapturingSlot<(error: Exception?, fulfilmentPointCategory: FulfilmentPointCategory?) -> Unit>
    private lateinit var paginatedFFPCategorySlot: CapturingSlot<(error: Exception?, response: PaginatedObject<FulfilmentPointCategory?>?) -> Unit>
    private lateinit var fulfilmentPointFeature: FulfilmentPointFeature

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        initMocks()
    }

    private fun initMocks() {
        fulfilmentPointFeature = FulfilmentPointFeature(fulfilmentPointRepository)
        ffpSlot = slot()
        paginatedFFPSlot = slot()
        ffpCategorySlot = slot()
        paginatedFFPCategorySlot = slot()
    }


    @Test
    fun `get fulfilment Points returns Data`() {
        //Given
        every {
            fulfilmentPointRepository.getFulfilmentPoints(
                FFPMocks.PAGE_SIZE,
                FFPMocks.PAGE,
                null,
                null,
                capture(paginatedFFPSlot)
            )
        } answers {
            paginatedFFPSlot.captured.invoke(null, FFPMocks.paginatedFFP)
        }
        //When
        fulfilmentPointFeature.getFulfilmentPoints(
            FFPMocks.PAGE_SIZE,
            FFPMocks.PAGE, null, null
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(FFPMocks.paginatedFFP, response)
        }
    }

    @Test
    fun `get fulfilment Points returns Exception`() {
        //Given
        every {
            fulfilmentPointRepository.getFulfilmentPoints(
                FFPMocks.PAGE_SIZE,
                FFPMocks.PAGE,
                null,
                null,
                capture(paginatedFFPSlot)
            )
        } answers {
            paginatedFFPSlot.captured.invoke(Exception(), null)
        }
        //When
        fulfilmentPointFeature.getFulfilmentPoints(
            FFPMocks.PAGE_SIZE,
            FFPMocks.PAGE, null, null
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Fulfilment Point By Id returns data`() {
        //Given
        every {
            fulfilmentPointRepository.getFulfilmentPointById(
                FFPMocks.fulfilmentPointID,
                null,
                capture(ffpSlot)
            )
        } answers {
            ffpSlot.captured.invoke(null, FFPMocks.fulfilmentPoint)
        }
        //When
        fulfilmentPointFeature.getFulfilmentPointById(
            FFPMocks.fulfilmentPointID,
            null
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(FFPMocks.fulfilmentPoint, response)
        }
    }

    @Test
    fun `get Fulfilment Point By Id returns exception`() {
        //Given
        every {
            fulfilmentPointRepository.getFulfilmentPointById(
                FFPMocks.fulfilmentPointID,
                null,
                capture(ffpSlot)
            )
        } answers {
            ffpSlot.captured.invoke(Exception(), null)
        }
        //When
        fulfilmentPointFeature.getFulfilmentPointById(
            FFPMocks.fulfilmentPointID,
            null
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get fulfilment Point Categories returns Data`() {
        //Given
        every {
            fulfilmentPointRepository.getFulfilmentPointCategories(
                FFPMocks.PAGE_SIZE,
                FFPMocks.PAGE,
                capture(paginatedFFPCategorySlot)
            )
        } answers {
            paginatedFFPCategorySlot.captured.invoke(null, FFPMocks.paginatedFFPCategories)
        }
        //When
        fulfilmentPointFeature.getFulfilmentPointCategories(
            FFPMocks.PAGE_SIZE,
            FFPMocks.PAGE
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(FFPMocks.paginatedFFPCategories, response)
        }
    }

    @Test
    fun `get fulfilment Point Categories returns Exception`() {
        //Given
        every {
            fulfilmentPointRepository.getFulfilmentPointCategories(
                FFPMocks.PAGE_SIZE,
                FFPMocks.PAGE,
                capture(paginatedFFPCategorySlot)
            )
        } answers {
            paginatedFFPCategorySlot.captured.invoke(Exception(), null)
        }
        //When
        fulfilmentPointFeature.getFulfilmentPointCategories(
            FFPMocks.PAGE_SIZE,
            FFPMocks.PAGE
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }

    @Test
    fun `get Fulfilment Point Category By Id returns data`() {
        //Given
        every {
            fulfilmentPointRepository.getFulfilmentPointCategoryById(
                FFPMocks.fulfilmentPointID,
                capture(ffpCategorySlot)
            )
        } answers {
            ffpCategorySlot.captured.invoke(null, FFPMocks.fulfilmentPointCategory)
        }
        //When
        fulfilmentPointFeature.getFulfilmentPointCategoryById(
            FFPMocks.fulfilmentPointID
        ) { error, response ->
            //Then
            assertEquals(null, error)
            assertEquals(FFPMocks.fulfilmentPointCategory, response)
        }
    }

    @Test
    fun `get Fulfilment Point Category By Id returns exception`() {
        //Given
        every {
            fulfilmentPointRepository.getFulfilmentPointCategoryById(
                FFPMocks.fulfilmentPointID,
                capture(ffpCategorySlot)
            )
        } answers {
            ffpCategorySlot.captured.invoke(Exception(), null)
        }
        //When
        fulfilmentPointFeature.getFulfilmentPointCategoryById(
            FFPMocks.fulfilmentPointID
        ) { error, response ->
            //Then
            assert(error is Exception)
            assertEquals(null, response)
        }
    }
}