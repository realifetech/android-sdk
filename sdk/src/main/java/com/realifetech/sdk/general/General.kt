package com.realifetech.sdk.general

import androidx.annotation.ColorInt
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.model.color.ColorType
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

    init {
        configuration.deviceId = physicalDeviceInfo.deviceId
    }

    @Volatile
    var isSdkReady: Boolean = false
        private set

    val deviceIdentifier: String
        get() = configuration.deviceId

    @Synchronized
    suspend fun registerDevice(): Result<Boolean> {
        sdkInitializationPrecondition.checkContextInitialized()
        return try {
            Timber.d("Sending register device request")
            val result = deviceRepository.registerDevice()
            isSdkReady = result is Result.Success

            Timber.d("Register device request result, is SDK ready = $isSdkReady")
            result
        } catch (exception: Exception) {
            Timber.e(exception, "Error while registering device")
            Result.Error(exception)
        }
    }

    suspend fun updateMyDeviceConsent(deviceConsent: DeviceConsent): com.realifetech.sdk.core.utils.Result<Boolean> {
        return try {
            deviceRepository.updateMyDeviceConsent(deviceConsent)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    fun setColor(@ColorInt color: Int, forType: ColorType) {
        colorPallet.apply {
            when (forType) {
                ColorType.PRIMARY -> colorPrimary = color
                ColorType.ON_PRIMARY -> colorOnPrimary = color
                ColorType.SURFACE -> colorSurface = color
                ColorType.ON_SURFACE -> colorOnSurface = color
                ColorType.NEUTRAL -> colorNeutral = color
            }
        }
    }
}