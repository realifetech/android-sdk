package com.realifetech.sdk.campaignautomation.domain

import com.realifetech.GetContentByExternalIdQuery
import com.realifetech.sdk.campaignautomation.data.datasource.CampaignAutomationDataSource
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CampaignAutomationRepositoryTest {

    @RelaxedMockK
    private lateinit var caDataSource: CampaignAutomationDataSource
    private lateinit var caSlot: CapturingSlot<(error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit>
    private lateinit var caRepository: CampaignAutomationRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        caRepository = CampaignAutomationRepository(caDataSource)
        caSlot = CapturingSlot()
    }

    @Test
    fun `get getContentByExternalId get data`() {
        every {
            caDataSource.getContentByExternalId(any(),capture(caSlot))
        } answers {
            caSlot.captured.invoke(null, CAMocks.mock)
        }
        caRepository.getContentByExternalId("banana"){
            error, response ->
            assertEquals(null, error)
            assertEquals(CAMocks.mock, response)
        }
    }

    @Test
    fun `get getContentByExternalId return Exception`() {
        every {
            caDataSource.getContentByExternalId(any(),capture(caSlot))
        } answers {
            caSlot.captured.invoke(Exception(), null)
        }

        caRepository.getContentByExternalId("banana"){
                error, response ->
            assert(error is Exception)
            assertEquals(null, response)
        }
    }

}