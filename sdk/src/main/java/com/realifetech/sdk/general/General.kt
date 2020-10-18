package com.realifetech.sdk.general

import android.util.Log
import com.realifetech.sdk.domain.LinearRetryPolicy
import com.realifetech.sdk.domain.Result
import com.realifetech.sdk.domain.RetryPolicy
import com.realifetech.sdk.general.data.PhysicalDeviceInfo
import com.realifetech.sdk.general.di.GeneralProvider
import com.realifetech.sdk.general.domain.DeviceConfiguration
import com.realifetech.sdk.general.domain.DeviceRegisterResponse
import com.realifetech.sdk.general.domain.SdkInitializationPrecondition
import java.lang.Exception
import java.util.concurrent.TimeUnit

class General private constructor() {

    internal val deviceRegistrationRetryPolicy: RetryPolicy =
        LinearRetryPolicy(DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS) {
            Log.d("RetryPolicy", "Retry, sending new register device request")
            instance.registerDevice()
        }

    val configuration = DeviceConfiguration()

    var isSdkReady: Boolean = false

    val deviceIdentified: String
        get() = PhysicalDeviceInfo(configuration.requireContext()).deviceId

    private object Holder {
        val instance = General()
    }

    /**
     * It will register the device to the backend.
     *
     * @throws RuntimeException if the SDK wasn't initialized first with [Context]. See [DeviceConfiguration.context]
     */
    @Synchronized
    fun registerDevice(): Result<DeviceRegisterResponse> {
        SdkInitializationPrecondition.checkContextInitialized()

        return try {
            val deviceRegistration = GeneralProvider(configuration.requireContext()).provideDeviceRegistration()

            Log.d("General", "Sending register device request")
            val result = deviceRegistration.registerDevice()
            isSdkReady = result is Result.Success

            Log.d("General", "Register device request result, is SDK ready = $isSdkReady")
            result
        } catch (exception: Exception) {
            Log.e("General", exception.message, exception)
            Result.Error(exception)
        }
    }

    companion object {
        private val DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(15)

        val instance: General by lazy { Holder.instance }
    }
}