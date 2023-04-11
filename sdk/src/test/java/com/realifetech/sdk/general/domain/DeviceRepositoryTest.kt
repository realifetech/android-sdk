package com.realifetech.sdk.general.domain

import com.realifetech.sdk.core.data.model.exceptions.NetworkException
import com.realifetech.sdk.core.domain.OAuthManager
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.DeviceNetworkDataSource
import com.realifetech.sdk.general.mocks.GeneralMocks.deviceRegisterResponse
import com.realifetech.sdk.general.mocks.GeneralMocks.networkError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DeviceRepositoryTest {

    @RelaxedMockK
    lateinit var deviceNetworkDataSource: DeviceNetworkDataSource

    @RelaxedMockK
    lateinit var oAuthManager: OAuthManager

    private lateinit var deviceRepository: DeviceRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deviceRepository = DeviceRepository(deviceNetworkDataSource) { oAuthManager }
    }

    @Test
    fun `register Device results successfully`() {
        every { deviceRepository.registerDevice() } returns Result.Success(true)
        every { oAuthManager.ensureActive() }returns Unit
        val result = deviceRepository.registerDevice()
        assert(result is Result.Success)
        assertEquals(deviceRegisterResponse, (result as Result.Success).data)
        verify { deviceRepository.registerDevice() }
    }

    @Test
    fun `register Device result with error `() {
        every { deviceRepository.registerDevice() }
            .returns(Result.Error(networkError))
        val result = deviceRepository.registerDevice()
        assert(result is Result.Error)
        assert((result as Result.Error).exception is NetworkException)
        assertEquals(networkError.message, result.exception.message)
        verify {
            oAuthManager.ensureActive()
            deviceRepository.registerDevice() }
    }
}