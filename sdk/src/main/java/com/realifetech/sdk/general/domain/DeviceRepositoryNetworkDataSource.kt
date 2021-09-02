package com.realifetech.sdk.general.domain

import com.realifetech.sdk.core.domain.NetworkException
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.DeviceInfo
import com.realifetech.sdk.general.data.DeviceRegisterRequest
import com.realifetech.sdk.general.data.DeviceRegisterResponse

internal class DeviceRepositoryNetworkDataSource (
    private val realifetechApiV3Service: RealifetechApiV3Service,
    private val deviceInfo: DeviceInfo
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