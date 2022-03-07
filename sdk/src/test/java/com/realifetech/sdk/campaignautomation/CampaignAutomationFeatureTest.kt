package com.realifetech.sdk.campaignautomation

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.realifetech.sdk.campaignautomation.data.model.RLTCreatableFactory
import com.realifetech.sdk.campaignautomation.data.model.RLTFetcher
import com.realifetech.sdk.campaignautomation.domain.CAMocks
import com.realifetech.sdk.campaignautomation.domain.CampaignAutomationRepository
import com.realifetech.sdk.sell.utils.MainCoroutineRule
import com.realifetechCa.GetContentByExternalIdQuery
import com.realifetechCa.type.ContentType
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CampaignAutomationFeatureTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    private lateinit var campaignAutomationRepo: CampaignAutomationRepository

    @RelaxedMockK
    private lateinit var rltFetcher: RLTFetcher

    private lateinit var captureSlot: CapturingSlot<(error: Error?, result: Boolean) -> Unit>
    private lateinit var contentSlot: CapturingSlot<(error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit>
    private lateinit var callback: CapturingSlot<(error: Exception?, response: List<View?>) -> Unit>

    private lateinit var caFeature: CampaignAutomationFeature

    private val location: String = "random location"


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        caFeature = CampaignAutomationFeature(
            campaignAutomationRepo,
            rltFetcher
        )
        captureSlot = slot()
        contentSlot = slot()
        callback = slot()
    }

    @Test
    fun `set up factories`() {
        val factory: Map<ContentType, RLTCreatableFactory<*>> = mutableMapOf()
        caFeature.factories(factory)
        verify { rltFetcher.setFactories(factory) }
    }

    @Test
    fun `get content by external Id return content`() {
        every {
            campaignAutomationRepo.getContentByExternalId(
                location,
                capture(contentSlot)
            )
        } answers {
            contentSlot.captured.invoke(null, CAMocks.mock)
        }
        caFeature.getContentByExternalId(location) { error, response ->
            assertEquals(null, error)
            assertEquals(CAMocks.mock, response)
        }
    }

    @Test
    fun `get content by external Id return Exception`() {
        every {
            campaignAutomationRepo.getContentByExternalId(
                location,
                capture(contentSlot)
            )
        } answers {
            contentSlot.captured.invoke(Exception(), null)
        }
        caFeature.getContentByExternalId(location) { error, response ->
            assert(error is Exception)
            assertEquals(null, response)
        }
    }
}