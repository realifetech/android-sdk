package com.realifetech.sdk.general.data

import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.model.device.DeviceRegisterRequest
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse
import com.realifetech.sdk.core.data.model.exceptions.NetworkException
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.Result

internal class DeviceNetworkDataSourceImpl(
    private val realifetechApiV3Service: RealifetechApiV3Service,
    private val deviceInfo: DeviceInfo,
    private val configuration: ConfigurationStorage,
) : DeviceNetworkDataSource {

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
        configuration.deviceId = deviceInfo.deviceId
        val networkResponse = realifetechApiV3Service.registerDevice(registerRequest).execute()
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