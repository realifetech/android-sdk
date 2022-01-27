package com.realifetech.sdk.identity.sso.domain

import com.realifetech.GetMyUserSSOQuery.GetMyUserSSO
import com.realifetech.sdk.identity.sso.data.SSODataSource
import com.realifetech.sdk.identity.sso.mocks.SSOMocks.expectedUser
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
    private lateinit var userSSOSlot: CapturingSlot<(error: Exception?, response: GetMyUserSSO?) -> Unit>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ssoRepository = SSORepository(ssoDatasource)
        userSSOSlot = slot()
    }

    @Test
    fun getMyUserSSO() {
    }

    @Test
    fun `get my User SSO results data`() {
        //Given
        every { ssoDatasource.getMyUserSSO(capture(userSSOSlot)) }
            .answers { userSSOSlot.captured.invoke(null, expectedUser) }
        //When
        ssoRepository.getMyUserSSO { error, response ->
            //Then
            Assert.assertEquals(null, error)
            Assert.assertEquals(expectedUser, response)

        }
    }

}