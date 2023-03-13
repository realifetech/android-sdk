package com.realifetech.sdk.general.data

import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse

interface DeviceNetworkDataSource {
    fun registerDevice(appVersion:String, callback: (error: Exception?, registered: Boolean?) -> Unit)
}