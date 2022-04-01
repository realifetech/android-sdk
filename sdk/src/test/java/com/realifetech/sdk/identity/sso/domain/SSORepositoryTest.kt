package com.realifetech.sdk.identity.sso.domain

import com.realifetech.fragment.FragmentUserAlias
import com.realifetech.sdk.identity.sso.data.SSODataSource
import com.realifetech.sdk.identity.sso.mocks.SSOMocks.expectedUserAlias
import io.mockk.CapturingSlot
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SSORepositoryTest {


    @RelaxedMockK
    lateinit var ssoDatasource: SSODataSource
    private lateinit var ssoRepository: SSORepository
    private lateinit var userSSOSlot: CapturingSlot<(error: Exception?, response: FragmentUserAlias?) -> Unit>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ssoRepository = SSORepository(ssoDatasource)
        userSSOSlot = slot()
    }

    @Test
    fun `get my User Alias results data`() {
        //Given
        every { ssoDatasource.getUserAlias(capture(userSSOSlot)) }
            .answers { userSSOSlot.captured.invoke(null, expectedUserAlias) }
        //When
        ssoRepository.getUserAlias { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(expectedUserAlias, response)

        }
    }

}