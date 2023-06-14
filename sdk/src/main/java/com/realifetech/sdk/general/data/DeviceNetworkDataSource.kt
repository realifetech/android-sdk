package com.realifetech.sdk.general.data

interface DeviceNetworkDataSource {
    suspend fun registerDevice(): com.realifetech.sdk.core.utils.Result<Boolean>
    suspend fun updateMyDeviceConsent(deviceConsent: DeviceConsent): com.realifetech.sdk.core.utils.Result<Boolean>
}