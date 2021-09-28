package com.realifetech.sdk.general.domain

import com.realifetech.sdk.core.data.model.exceptions.NetworkException
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.DeviceNetworkDataSource
import com.realifetech.sdk.general.mocks.GeneralMocks.deviceRegisterResponse
import com.realifetech.sdk.general.mocks.GeneralMocks.networkError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DeviceRepositoryTest {

    @RelaxedMockK
    lateinit var deviceNetworkDataSource: DeviceNetworkDataSource

    private lateinit var deviceRepository: DeviceRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deviceRepository = DeviceRepository(deviceNetworkDataSource)
    }

    @Test
    fun `register Device results successfully`() {
        every { deviceNetworkDataSource.registerDevice() } returns Result.Success(
            deviceRegisterResponse
        )
        val result = deviceRepository.registerDevice()
        assert(result is Result.Success)
        assertEquals(deviceRegisterResponse, (result as Result.Success).data)
        verify { deviceNetworkDataSource.registerDevice() }
    }

    @Test
    fun `register Device result with error `() {
        every { deviceNetworkDataSource.registerDevice() }
            .returns(Result.Error(networkError))
        val result = deviceRepository.registerDevice()
        assert(result is Result.Error)
        assert((result as Result.Error).exception is NetworkException)
        assertEquals(networkError.message, result.exception.message)
        verify { deviceNetworkDataSource.registerDevice() }
    }
}