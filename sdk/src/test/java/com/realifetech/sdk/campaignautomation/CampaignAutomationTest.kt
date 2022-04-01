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

class CampaignAutomationTest {

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
    private lateinit var callback: (error: Exception?, response: List<View?>) -> Unit
    private val factory: Map<ContentType, RLTCreatableFactory<*>> = mutableMapOf()


    private lateinit var caFeature: CampaignAutomation

    private val location: String = "random location"


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        caFeature = CampaignAutomation(
            campaignAutomationRepo,
            rltFetcher
        )
        captureSlot = slot()
        contentSlot = slot()
        callback = mockk()
    }

    @Test
    fun `CA feature fetch without factories`() {
        // WHEN
        caFeature.fetch(location, callback)
        // THEN
        verify { rltFetcher.fetch(location, callback) }
    }

    @Test
    fun `CA feature fetch with factories`() {
        // WHEN
        caFeature.fetch(location, factory, callback)
        // THEN
        verify { rltFetcher.fetch(location, factory, callback) }
    }

    @Test
    fun `set up factories`() {
        // WHEN
        caFeature.factories(factory)
        // THEN
        verify { rltFetcher.setFactories(factory) }
    }

    @Test
    fun `get content by external Id return content`() {
        // GIVEN
        every {
            campaignAutomationRepo.getContentByExternalId(
                location,
                capture(contentSlot)
            )
        } answers {
            contentSlot.captured.invoke(null, CAMocks.mock)
        }
        // WHEN
        caFeature.getContentByExternalId(location) { error, response ->
            // THEN
            assertEquals(null, error)
            assertEquals(CAMocks.mock, response)
        }
    }

    @Test
    fun `get content by external Id return Exception`() {
        // GIVEN
        every {
            campaignAutomationRepo.getContentByExternalId(
                location,
                capture(contentSlot)
            )
        } answers {
            contentSlot.captured.invoke(Exception(), null)
        }
        // WHEN
        caFeature.getContentByExternalId(location) { error, response ->
            // THEN
            assert(error is Exception)
            assertEquals(null, response)
        }
    }
}