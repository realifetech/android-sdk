package com.realifetech.sdk.general.data

import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse

interface DeviceNetworkDataSource {
    fun registerDevice(callback: (error: Exception?, registered: Boolean?) -> Unit)
}