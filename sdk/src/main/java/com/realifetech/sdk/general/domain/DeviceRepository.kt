package com.realifetech.sdk.general.domain

import com.realifetech.sdk.core.domain.LinearRetryPolicy
import com.realifetech.sdk.core.domain.OAuthManager
import com.realifetech.sdk.core.domain.RetryPolicy
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.DeviceConsent
import com.realifetech.sdk.general.data.DeviceNetworkDataSource
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val dataSource: DeviceNetworkDataSource,
    private val oAuthManager: Lazy<OAuthManager>
) {
    private val retryPolicy: RetryPolicy = LinearRetryPolicy(DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS) {
        Timber.tag("RetryPolicy").d("Retry, sending new register device request")
        CoroutineScope(Dispatchers.Default).launch {
            registerDevice()
        }
    }

    @Synchronized
    suspend fun registerDevice(): com.realifetech.sdk.core.utils.Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                oAuthManager.get().ensureActive()
                val result = dataSource.registerDevice()
                if (result is Result.Error) {
                    oAuthManager.get().ensureActive()
                    retryPolicy.execute()
                } else if (result is Result.Success) {
                    retryPolicy.cancel()
                }
                result
            } catch (e: Exception) {
                retryPolicy.execute()
                Result.Error(e)
            }
        }
    }

    suspend fun updateMyDeviceConsent(deviceConsent: DeviceConsent): com.realifetech.sdk.core.utils.Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                dataSource.updateMyDeviceConsent(deviceConsent)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }

    companion object {
        private val DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(10)
    }
}