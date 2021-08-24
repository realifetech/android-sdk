package com.realifetech.sdk.general.data.repositories

import com.realifetech.sdk.domain.NetworkException
import com.realifetech.sdk.core.domain.Result
import com.realifetech.sdk.general.data.DeviceInfo
import com.realifetech.sdk.general.domain.DeviceRegisterRequest
import com.realifetech.sdk.general.domain.DeviceRegisterResponse
import com.realifetech.sdk.general.network.DeviceApiService

internal class DeviceRepositoryNetworkDataSource(
    private val deviceApiService: DeviceApiService,
    private val deviceInfo: DeviceInfo
) : DeviceRepository.DataSource {

    private val registerRequest: DeviceRegisterRequest
        get() {
            return DeviceRegisterRequest(
                deviceInfo.deviceId,
                ANDROID,
                deviceInfo.appVersionName,
                deviceInfo.osVersion,
                deviceInfo.model,
                deviceInfo.manufacturer,
                deviceInfo.screenWidthPixels,
                deviceInfo.screenHeightPixels,
                deviceInfo.isBluetoothEnabled,
                deviceInfo.isWifiOn,
                deviceInfo.isWifiConnected
            )
        }

    override fun registerDevice(): Result<DeviceRegisterResponse> {
        val networkResponse = deviceApiService.registerDevice(registerRequest).execute()
        return if (networkResponse.isSuccessful) {
            val response = networkResponse.body() ?: DeviceRegisterResponse(-1, "", "")
            Result.Success(response)
        } else {
            val errorMessage = networkResponse.errorBody()?.string().orEmpty()
            Result.Error(NetworkException(networkResponse.code(), errorMessage))
        }
    }

    private companion object {
        private const val ANDROID = "ANDROID"
    }
}