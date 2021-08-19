package com.realifetech.sdk.general

import android.content.Context
import android.util.Log
import androidx.annotation.ColorInt
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.realifetech.core_sdk.domain.CoreConfiguration.context
import com.realifetech.sdk.domain.LinearRetryPolicy
import com.realifetech.sdk.domain.Result
import com.realifetech.sdk.domain.RetryPolicy
import com.realifetech.sdk.general.data.color.ColorType
import com.realifetech.sdk.general.data.color.ColorType.*
import com.realifetech.sdk.general.di.GeneralProvider
import com.realifetech.sdk.general.domain.DeviceConfiguration
import com.realifetech.sdk.general.domain.DeviceRegisterResponse
import com.realifetech.sdk.general.domain.SdkInitializationPrecondition
import com.realifetech.sdk.utils.ColorPallet.colorNeutral
import com.realifetech.sdk.utils.ColorPallet.colorOnPrimary
import com.realifetech.sdk.utils.ColorPallet.colorOnSurface
import com.realifetech.sdk.utils.ColorPallet.colorPrimary
import com.realifetech.sdk.utils.ColorPallet.colorSurface
import java.util.concurrent.TimeUnit

class General private constructor() {

    internal val deviceRegistrationRetryPolicy: RetryPolicy =
        LinearRetryPolicy(DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS) {
            Log.d("RetryPolicy", "Retry, sending new register device request")
            instance.registerDevice()
        }

    val configuration = DeviceConfiguration()

    var isSdkReady: Boolean = false
        private set

    val deviceIdentifier: String
        get() = AdvertisingIdClient.getAdvertisingIdInfo(configuration.requireContext()).id + ":" + context.packageName

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
            val deviceRegistration =
                GeneralProvider(configuration.requireContext()).provideDeviceRegistration()

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


    fun setColor(@ColorInt color: Int, forType: ColorType) {
        when (forType) {
            PRIMARY -> colorPrimary = color
            ON_PRIMARY -> colorOnPrimary = color
            SURFACE -> colorSurface = color
            ON_SURFACE -> colorOnSurface = color
            NEUTRAL -> colorNeutral = color
        }
    }

    companion object {
        private val DEVICE_REGISTRATION_RETRY_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(10)

        val instance: General by lazy { Holder.instance }
    }
}