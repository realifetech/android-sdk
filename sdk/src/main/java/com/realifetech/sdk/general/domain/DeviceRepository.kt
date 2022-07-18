package com.realifetech.sdk.general.domain

import android.util.Log
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse
import com.realifetech.sdk.core.domain.LinearRetryPolicy
import com.realifetech.sdk.core.domain.OAuthManager
import com.realifetech.sdk.core.domain.RetryPolicy
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.DeviceNetworkDataSource
import dagger.Lazy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val dataSource: DeviceNetworkDataSource,
    private val oAuthManager: Lazy<OAuthManager>
) {
    private val retryPolicy: RetryPolicy =
        LinearRetryPolicy(DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS) {
            Log.d("RetryPolicy", "Retry, sending new register device request")
            registerDevice()
        }


    @Synchronized
    fun registerDevice(): Result<DeviceRegisterResponse> {
        val accessToken = oAuthManager.get()
        accessToken.ensureActive()
        val response = dataSource.registerDevice()
        if (response is Result.Error) {
            accessToken.ensureActive()
            Log.e("DeviceRepository", "Register device Error: ${response.exception.message}")
            retryPolicy.execute()
        } else {
            retryPolicy.cancel()
        }

        return response
    }

    companion object {
        private val DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(10)
    }
}