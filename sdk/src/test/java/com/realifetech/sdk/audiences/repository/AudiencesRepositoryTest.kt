package com.realifetech.sdk.audiences.repository

import com.realifetech.sdk.audiences.data.AudiencesDataSource
import com.realifetech.sdk.audiences.mocks.AudiencesMocks.externalAudienceId
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class AudiencesRepositoryTest {

    @RelaxedMockK
    lateinit var audiencesDataSource: AudiencesDataSource
    lateinit var audiencesRepository: AudiencesRepository
    private lateinit var captureSlot: CapturingSlot<(error: Error?, result: Boolean) -> Unit>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        audiencesRepository = AudiencesRepository(audiencesDataSource)
        captureSlot = slot()
    }

    @Test
    fun `belongs To Audience With ExternalId returns response`() {
        every {
            audiencesDataSource.belongsToAudienceWithExternalId(
                externalAudienceId,
                capture(captureSlot)
            )
        }.answers { captureSlot.captured.invoke(null, true) }
        audiencesRepository.belongsToAudienceWithExternalId(externalAudienceId) { error, result ->
            assertNull(error)
            assertEquals(true, result)
        }
    }


}