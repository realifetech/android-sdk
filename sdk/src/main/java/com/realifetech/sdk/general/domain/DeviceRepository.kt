package com.realifetech.sdk.general.domain

import com.realifetech.sdk.core.domain.LinearRetryPolicy
import com.realifetech.sdk.core.domain.OAuthManager
import com.realifetech.sdk.core.domain.RetryPolicy
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.DeviceConsent
import com.realifetech.sdk.general.data.DeviceNetworkDataSource
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val dataSource: DeviceNetworkDataSource,
    private val oAuthManager: OAuthManager
) {
    private val tag = this::class.java.simpleName
    private lateinit var result: Result<Boolean>

    private val retryPolicy: RetryPolicy =
        LinearRetryPolicy(DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS) {
            Timber.tag(tag).d("Retry, sending new register device request")
            registerDevice()
        }

    @Synchronized
    fun registerDevice(): Result<Boolean> {
        oAuthManager.ensureActive()
        dataSource.registerDevice { error, registered ->
            error?.let {
                Timber.tag(tag).e( "Register device Error: ${it.message}")
                retryPolicy.execute()
                result = Result.Error(it)
            }
            registered?.let {
                retryPolicy.cancel()
                result = Result.Success(it)
            }
        }
        return result
    }

    fun updateMyDeviceConsent(deviceConsent: DeviceConsent, callback: (error: Exception?, result: Boolean?) -> Unit) {
        dataSource.updateMyDeviceConsent(deviceConsent, callback)
    }

    companion object {
        private val DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(10)
    }
}