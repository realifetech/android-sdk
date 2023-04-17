package com.realifetech.sdk.general.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.realifetech.sdk.core.data.model.exceptions.NetworkException
import com.realifetech.sdk.core.domain.OAuthManager
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.DeviceConsent
import com.realifetech.sdk.general.data.DeviceNetworkDataSource
import com.realifetech.sdk.general.data.LocationGranularStatus
import com.realifetech.sdk.general.mocks.GeneralMocks.deviceRegisterResponse
import com.realifetech.sdk.general.mocks.GeneralMocks.networkError
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DeviceRepositoryTest {

    @RelaxedMockK
    lateinit var deviceNetworkDataSource: DeviceNetworkDataSource

    @RelaxedMockK
    lateinit var oAuthManager: OAuthManager

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var deviceRepository: DeviceRepository

    lateinit var deviceConsent: DeviceConsent

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deviceRepository = DeviceRepository(deviceNetworkDataSource) { oAuthManager }
        MockitoAnnotations.initMocks(this)
        deviceConsent = DeviceConsent(true, true, true, LocationGranularStatus.ALWAYS, true, true)
    }

    @Test
    fun `register Device results successfully`() {
        every { deviceRepository.registerDevice() } returns Result.Success(
            deviceRegisterResponse
        )
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

    @Test
    fun `test update my device consent Success`() {
        `when`(deviceNetworkDataSource.updateMyDeviceConsent(deviceConsent,
            Any() as (Exception?, Boolean?) -> Unit
        )).thenAnswer {
            val callback = it.arguments[1] as (Throwable?, Boolean?) -> Unit
            callback(null, true)
        }

        val result = deviceRepository.updateMyDeviceConsent(deviceConsent)

        assertEquals(Result.Success(true), result)
    }

    @Test
    fun `test update my device consent Error`() {
        val errorMessage = "Error test"
        `when`(deviceNetworkDataSource.updateMyDeviceConsent(deviceConsent,
            Any() as (Exception?, Boolean?) -> Unit
        )).thenAnswer {
            val callback = it.arguments[1] as (Throwable?, Boolean?) -> Unit
            callback(Throwable(errorMessage), null)
        }

        val result = deviceRepository.updateMyDeviceConsent(deviceConsent)

        assertEquals(Result.Error(Throwable(errorMessage) as Exception), result)
    }

}