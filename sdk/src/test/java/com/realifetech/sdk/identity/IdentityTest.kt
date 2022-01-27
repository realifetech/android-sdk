package com.realifetech.sdk.identity

import com.realifetech.sdk.identity.sso.SSOFeature
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class IdentityTest {

    @RelaxedMockK
    lateinit var ssoFeature: SSOFeature
    private lateinit var identity: Identity

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        identity = Identity(ssoFeature)
    }


    @Test
    fun getSSO() {
        val sso = identity.getSSO()
        Assert.assertEquals(ssoFeature, sso)
    }
}