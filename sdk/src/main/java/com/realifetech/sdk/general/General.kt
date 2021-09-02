package com.realifetech.sdk.general

import android.content.Context
import android.util.Log
import androidx.annotation.ColorInt
import com.realifetech.sdk.core.domain.RLTConfiguration
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.data.PhysicalDeviceInfo
import com.realifetech.sdk.core.data.color.ColorType
import com.realifetech.sdk.core.data.color.ColorType.*
import com.realifetech.sdk.general.domain.DeviceRepository
import com.realifetech.sdk.general.data.DeviceRegisterResponse
import com.realifetech.sdk.general.domain.SdkInitializationPrecondition
import com.realifetech.sdk.core.utils.ColorPallet

import javax.inject.Inject

class General (
    private val deviceRepository: DeviceRepository,
    private val sdkInitializationPrecondition: SdkInitializationPrecondition,
    private val physicalDeviceInfo: PhysicalDeviceInfo,
    private val colorPallet: ColorPallet
) {

    var isSdkReady: Boolean = false
        private set

    val deviceIdentifier: String
        get() = physicalDeviceInfo.deviceId

    /**
     * It will register the device to the backend.
     *
     * @throws RuntimeException if the SDK wasn't initialized first with [Context]. See [RLTConfiguration.context]
     */
    @Synchronized
    fun registerDevice(): Result<DeviceRegisterResponse> {
        sdkInitializationPrecondition.checkContextInitialized()
        return try {
            Log.d("General", "Sending register device request")
            val result = deviceRepository.registerDevice()
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
            when (forType) {
                PRIMARY -> colorPrimary = color
                ON_PRIMARY -> colorOnPrimary = color
                SURFACE -> colorSurface = color
                ON_SURFACE -> colorOnSurface = color
                NEUTRAL -> colorNeutral = color
            }
        }
    }

}