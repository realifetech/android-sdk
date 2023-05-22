package com.realifetech.sdk.general.data

import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse
import com.realifetech.type.DeviceConsentInput

interface DeviceNetworkDataSource {
    fun registerDevice(callback: (error: Exception?, registered: Boolean?) -> Unit)
    fun updateMyDeviceConsent(input: DeviceConsent, callback: (error: Exception?, result: Boolean?) -> Unit)
}