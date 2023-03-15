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
    private lateinit var result: com.realifetech.sdk.core.utils.Result<Boolean>
    private val retryPolicy: RetryPolicy =
        LinearRetryPolicy(DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS) {
            Log.d("RetryPolicy", "Retry, sending new register device request")
            registerDevice()
        }


    @Synchronized
    fun registerDevice(): Result<Boolean> {
        val accessToken = oAuthManager.get()
        accessToken.ensureActive()
        dataSource.registerDevice { error, registered ->
            error?.let {
                accessToken.ensureActive()
                Log.e("DeviceRepository", "Register device Error: ${it.message}")
                retryPolicy.execute()
                result =  Result.Error(it)
            }
            registered?.let {
                retryPolicy.cancel()
                result = Result.Success(it)
            }
        }
        return result
    }

    companion object {
        private val DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(10)
    }
}