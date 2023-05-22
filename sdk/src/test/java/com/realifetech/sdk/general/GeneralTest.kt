package com.realifetech.sdk.general

import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.model.color.ColorType
import com.realifetech.sdk.core.utils.ColorPallet
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.domain.DeviceRepository
import com.realifetech.sdk.general.domain.SdkInitializationPrecondition
import com.realifetech.sdk.general.mocks.GeneralMocks.deviceRegisterResponse
import com.realifetech.sdk.general.mocks.GeneralMocks.deviceRequest
import com.realifetech.sdk.general.mocks.GeneralMocks.networkError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GeneralTest {

    @RelaxedMockK
    lateinit var deviceRepository: DeviceRepository

    @RelaxedMockK
    lateinit var sdkInitializationPrecondition: SdkInitializationPrecondition

    @RelaxedMockK
    lateinit var configuration: ConfigurationStorage

    @RelaxedMockK
    lateinit var colorPallet: ColorPallet

    private lateinit var general: General

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        general =
            General(deviceRepository, sdkInitializationPrecondition, configuration, colorPallet)
    }

    @Test
    fun getDeviceIdentifier() {
        every { configuration.deviceId } returns deviceRequest.token
        assertEquals(deviceRequest.token, general.deviceIdentifier)
    }

    @Test
    fun `register Device results with success`() {
        every { sdkInitializationPrecondition.checkContextInitialized() } returns Unit
        every { deviceRepository.registerDevice() } returns Result.Success(true)
        val result = general.registerDevice()
        assert(result is Result.Success)
        assertEquals(deviceRegisterResponse, (result as Result.Success).data)
        assertEquals(true, general.isSdkReady)
        verify { sdkInitializationPrecondition.checkContextInitialized() }

    }

    @Test
    fun `register Device results with failure`() {
        every { sdkInitializationPrecondition.checkContextInitialized() } returns Unit
        every { deviceRepository.registerDevice() } throws networkError
        val result = general.registerDevice()
        assert(result is Result.Error)
        assertEquals(networkError.message, (result as Result.Error).exception.message)
        assertEquals(false, general.isSdkReady)
        verify { sdkInitializationPrecondition.checkContextInitialized() }
    }

    @Test
    fun setColor() {
        general.setColor(PRIMARY, ColorType.PRIMARY)
        verify { colorPallet.colorPrimary = PRIMARY }

        general.setColor(ON_PRIMARY, ColorType.ON_PRIMARY)
        verify { colorPallet.colorOnPrimary = ON_PRIMARY }

        general.setColor(SURFACE, ColorType.SURFACE)
        verify { colorPallet.colorSurface = SURFACE }

        general.setColor(ON_SURFACE, ColorType.ON_SURFACE)
        verify { colorPallet.colorOnSurface = ON_SURFACE }

        general.setColor(NEUTRAL, ColorType.NEUTRAL)
        verify { colorPallet.colorNeutral = NEUTRAL }

    }

    companion object {
        private const val PRIMARY = 1
        private const val ON_PRIMARY = 2
        private const val SURFACE = 3
        private const val ON_SURFACE = 4
        private const val NEUTRAL = 5
    }
}