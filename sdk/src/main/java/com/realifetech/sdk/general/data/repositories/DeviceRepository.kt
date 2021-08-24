package com.realifetech.sdk.general.data.repositories

import android.util.Log
import com.realifetech.sdk.core.domain.Result
import com.realifetech.sdk.domain.RetryPolicy
import com.realifetech.sdk.general.domain.DeviceRegisterResponse

internal class DeviceRepository(private val dataSource: DataSource, private val retryPolicy: RetryPolicy) {

    @Synchronized
    fun registerDevice(): Result<DeviceRegisterResponse> {
        val response = dataSource.registerDevice()

        if (response is Result.Error) {
            Log.e("DeviceRepository", "Register device Error: ${response.exception.message}")
            retryPolicy.execute()
        } else {
            retryPolicy.cancel()
        }

        return response
    }

    interface DataSource {
        fun registerDevice(): Result<DeviceRegisterResponse>
    }
}