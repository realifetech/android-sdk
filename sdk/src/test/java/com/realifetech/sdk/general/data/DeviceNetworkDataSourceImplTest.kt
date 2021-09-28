package com.realifetech.sdk.general.data

import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse
import com.realifetech.sdk.core.data.model.exceptions.NetworkException
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.mocks.GeneralMocks.deviceRegisterResponse
import com.realifetech.sdk.general.mocks.GeneralMocks.deviceRequest
import com.realifetech.sdk.general.mocks.GeneralMocks.errorBody
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class DeviceNetworkDataSourceImplTest {

    @RelaxedMockK
    lateinit var configurationStorage: ConfigurationStorage

    @RelaxedMockK
    lateinit var deviceInfo: PhysicalDeviceInfo

    @RelaxedMockK
    lateinit var realifetechApiV3Service: RealifetechApiV3Service

    private lateinit var deviceNetworkDataSource: DeviceNetworkDataSourceImpl
    private lateinit var registerDeviceResponse: Response<DeviceRegisterResponse>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deviceNetworkDataSource =
            DeviceNetworkDataSourceImpl(realifetechApiV3Service, deviceInfo, configurationStorage)
        initMockedFields()

    }

    private fun initMockedFields() {
        registerDeviceResponse = mockk()
        deviceInfo.apply {
            every { deviceId } returns deviceRequest.token
            every { deviceInfo.appVersionName } returns deviceRequest.appVersion
            every { deviceInfo.osVersion } returns deviceRequest.osVersion
            every { deviceInfo.model } returns deviceRequest.model
            every { deviceInfo.manufacturer } returns deviceRequest.manufacturer
            every { deviceInfo.screenWidthPixels } returns deviceRequest.screenHeight
            every { deviceInfo.screenHeightPixels } returns deviceRequest.screenHeight
            every { deviceInfo.isBluetoothEnabled } returns deviceRequest.bluetoothOn
            every { deviceInfo.isWifiOn } returns deviceRequest.wifiOn
            every { deviceInfo.isWifiConnected } returns deviceRequest.wifiConnected
        }
    }

    @Test
    fun `register Device results successfully`() {
        every {
            realifetechApiV3Service.registerDevice(any()).execute()
        } returns registerDeviceResponse
        every { registerDeviceResponse.isSuccessful } returns true
        every { registerDeviceResponse.body() } returns deviceRegisterResponse
        val result: Result<DeviceRegisterResponse> = deviceNetworkDataSource.registerDevice()
        assert(result is Result.Success)
        assertEquals(deviceRegisterResponse, (result as Result.Success).data)
        verify { configurationStorage.deviceId = deviceRequest.token }
        verify { realifetechApiV3Service.registerDevice(any()) }
    }

    @Test
    fun `register Device results successfully with null response`() {
        registerSuccessfully()
        every { registerDeviceResponse.body() } returns null
        val result: Result<DeviceRegisterResponse> = deviceNetworkDataSource.registerDevice()
        assert(result is Result.Success)
        assertEquals(DeviceRegisterResponse(-1, "", ""), (result as Result.Success).data)
        verify { configurationStorage.deviceId = deviceRequest.token }
        verify { realifetechApiV3Service.registerDevice(any()) }
    }

    private fun registerSuccessfully() {
        every {
            realifetechApiV3Service.registerDevice(any()).execute()
        } returns registerDeviceResponse
        every { registerDeviceResponse.isSuccessful } returns true
    }

    @Test
    fun `register Device results with failure`() {
        registerFail()
        every { registerDeviceResponse.errorBody()?.string() } returns errorBody.message
        val result: Result<DeviceRegisterResponse> = deviceNetworkDataSource.registerDevice()
        assert(result is Result.Error)
        assert((result as Result.Error).exception is NetworkException)
        assertEquals(errorBody.message, result.exception.message)
        verify { configurationStorage.deviceId = deviceRequest.token }
        verify { realifetechApiV3Service.registerDevice(any()) }
    }

    @Test
    fun `register Device results with failure and null message`() {
        registerFail()
        every { registerDeviceResponse.errorBody()?.string() } returns null
        val result: Result<DeviceRegisterResponse> = deviceNetworkDataSource.registerDevice()
        assert(result is Result.Error)
        assert((result as Result.Error).exception is NetworkException)
        assertEquals("", result.exception.message)
        verify { configurationStorage.deviceId = deviceRequest.token }
        verify { realifetechApiV3Service.registerDevice(any()) }
    }

    @Test
    fun `register Device results with failure and null error body`() {
        registerFail()
        every { registerDeviceResponse.errorBody() } returns null
        val result: Result<DeviceRegisterResponse> = deviceNetworkDataSource.registerDevice()
        assert(result is Result.Error)
        assert((result as Result.Error).exception is NetworkException)
        assertEquals("", result.exception.message)
        verify { configurationStorage.deviceId = deviceRequest.token }
        verify { realifetechApiV3Service.registerDevice(any()) }
    }
    private fun registerFail() {
        every {
            realifetechApiV3Service.registerDevice(any()).execute()
        } returns registerDeviceResponse
        every { registerDeviceResponse.isSuccessful } returns false
        every { registerDeviceResponse.code() } returns errorBody.code
    }
}