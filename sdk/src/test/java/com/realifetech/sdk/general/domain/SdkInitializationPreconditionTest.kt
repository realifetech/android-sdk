package com.realifetech.sdk.general.domain

import android.content.Context
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class SdkInitializationPreconditionTest {

    @RelaxedMockK
    lateinit var context: Context

    private lateinit var sdkInitializationPrecondition: SdkInitializationPrecondition

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

    }

    @Test
    fun `check Context Initialized don't throw errors`() {
        sdkInitializationPrecondition = SdkInitializationPrecondition(context)
        var exception: Exception? = null
        try {
            sdkInitializationPrecondition.checkContextInitialized()
        } catch (e: Exception) {
            exception = e
        }
        assertEquals(null, exception)
    }

    @Test
    fun checkContextInitialized() {
        sdkInitializationPrecondition = SdkInitializationPrecondition(null)
        assertThrows(RuntimeException::class.java) {
            sdkInitializationPrecondition.checkContextInitialized()
        }
    }
}