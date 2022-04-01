package com.realifetech.sdk.identity.sso

import com.realifetech.fragment.FragmentUserAlias
import com.realifetech.sdk.identity.sso.domain.SSORepository
import com.realifetech.sdk.identity.sso.mocks.SSOMocks
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SSOFeatureTest {

    @RelaxedMockK
    lateinit var ssoRepository: SSORepository
    private lateinit var ssoFeature: SSOFeature
    private lateinit var userSSOSlot: CapturingSlot<(error: Exception?, response: FragmentUserAlias?) -> Unit>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ssoFeature = SSOFeature(ssoRepository)
        userSSOSlot = slot()
    }

    @Test
    fun `get my User Alias results data`() {
        //Given
        every { ssoRepository.getUserAlias(capture(userSSOSlot)) }
            .answers { userSSOSlot.captured.invoke(null, SSOMocks.expectedUserAlias) }
        //When
        ssoFeature.getUserAlias { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(SSOMocks.expectedUserAlias, response)

        }
    }
}