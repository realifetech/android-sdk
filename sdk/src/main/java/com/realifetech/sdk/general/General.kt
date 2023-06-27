package com.realifetech.sdk.general

import android.util.Log
import androidx.annotation.ColorInt
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.model.color.ColorType
import com.realifetech.sdk.core.data.model.color.ColorType.*
import com.realifetech.sdk.core.utils.ColorPallet
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.DeviceConsent
import com.realifetech.sdk.general.data.PhysicalDeviceInfo
import com.realifetech.sdk.general.domain.DeviceRepository
import com.realifetech.sdk.general.domain.SdkInitializationPrecondition
import timber.log.Timber
import javax.inject.Inject

class General @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val sdkInitializationPrecondition: SdkInitializationPrecondition,
    private val configuration: ConfigurationStorage,
    private val colorPallet: ColorPallet,
    physicalDeviceInfo: PhysicalDeviceInfo
) {
    val TAG = General::class.simpleName

    init {
        configuration.deviceId = physicalDeviceInfo.deviceId
    }

    var isSdkReady: Boolean = false
        private set

    val deviceIdentifier: String
        get() = configuration.deviceId


    @Synchronized
    fun registerDevice(): Result<Boolean> {
        sdkInitializationPrecondition.checkContextInitialized()
        return try {
            Timber.tag(TAG).d("Sending register device request")
            val result = deviceRepository.registerDevice()
            isSdkReady = result is Result.Success
            Timber.tag(TAG).d("Register device request result, is SDK ready = $isSdkReady")
            result
        } catch (exception: Exception) {
            Timber.tag(TAG).e(exception)
            Result.Error(exception)
        }
    }

    fun updateMyDeviceConsent(deviceConsent: DeviceConsent, callback: (error: Exception?, result: Boolean?) -> Unit) {
        return deviceRepository.updateMyDeviceConsent(deviceConsent, callback)
    }

    fun setColor(@ColorInt color: Int, forType: ColorType) {
        colorPallet.apply {
            when (forType ) {
                PRIMARY -> colorPrimary = color
                ON_PRIMARY -> colorOnPrimary = color
                SURFACE -> colorSurface = color
                ON_SURFACE -> colorOnSurface = color
                NEUTRAL -> colorNeutral = color
            }
        }
    }

}