package com.realifetech.sdk.general

import android.util.Log
import androidx.annotation.ColorInt
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.model.color.ColorType
import com.realifetech.sdk.core.data.model.color.ColorType.*
import com.realifetech.sdk.core.data.model.device.DeviceRegisterResponse
import com.realifetech.sdk.core.utils.ColorPallet
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.domain.DeviceRepository
import com.realifetech.sdk.general.domain.SdkInitializationPrecondition
import kotlinx.coroutines.selects.select
import javax.inject.Inject

class General @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val sdkInitializationPrecondition: SdkInitializationPrecondition,
    private val configuration: ConfigurationStorage,
    private val colorPallet: ColorPallet
) {

    var isSdkReady: Boolean = false
        private set

    val deviceIdentifier: String
        get() = configuration.deviceId


    @Synchronized
    fun registerDevice(appVersion: String): Result<Boolean> {
        sdkInitializationPrecondition.checkContextInitialized()
        return try {
            Log.d("General", "Sending register device request")
            val result = deviceRepository.registerDevice(appVersion)
            isSdkReady = result is Result.Success

            Log.d("General", "Register device request result, is SDK ready = $isSdkReady")
            result
        } catch (exception: Exception) {
            Log.e("General", exception.message, exception)
            Result.Error(exception)
        }
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