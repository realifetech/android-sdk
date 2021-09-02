package com.realifetech.sdk.general.domain

import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.DeviceRegisterResponse

interface DeviceNetworkDataSource {
    fun registerDevice(): Result<DeviceRegisterResponse>
}