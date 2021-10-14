package com.realifetech.sdk.audiences

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.realifetech.sdk.audiences.mocks.AudiencesMocks.externalAudienceId
import com.realifetech.sdk.audiences.repository.AudiencesRepository
import com.realifetech.sdk.core.utils.hasNetworkConnection
import com.realifetech.sdk.sell.utils.MainCoroutineRule
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class AudiencesTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var audiencesRepository: AudiencesRepository

    @RelaxedMockK
    lateinit var context: Context

    private lateinit var audiences: Audiences

    private lateinit var captureSlot: CapturingSlot<(error: Error?, result: Boolean) -> Unit>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        audiences = Audiences(audiencesRepository, testDispatcher, testDispatcher, context)
        captureSlot = slot()
    }

    @Test
    fun `device Is Member Of Audience return success`() = runBlockingTest {
        every {
            audiencesRepository.belongsToAudienceWithExternalId(
                externalAudienceId,
                capture(captureSlot)
            )
        } answers {
            captureSlot.captured.invoke(null, true)
        }
        every { context.hasNetworkConnection } returns true
        audiences.deviceIsMemberOfAudience(externalAudienceId) { error, result ->
            assertNull(error)
            assertEquals(true, result)
        }
    }

    @Test
    fun `check device Is Member Of Audience when there is no connection`() = runBlockingTest {

        every { context.hasNetworkConnection } returns false
        audiences.deviceIsMemberOfAudience(externalAudienceId) { error, result ->

            assertNotNull(error)
            assertEquals("No Internet connection", error?.message)
            assertEquals(false, result)
        }
    }
}