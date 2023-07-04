package com.realifetech.sdk.general.data

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.utils.isWifiConnected
import com.realifetech.sdk.core.utils.isWifiOn
import java.util.*
import javax.inject.Inject

/**
 * Returns information about the current device & based on the passed [Context]
 * Will be excluded from unit test coverage as it contains context, use UI test instead.
 */
open class PhysicalDeviceInfo @Inject constructor(
    private val context: Context,
    private val configurationStorage: ConfigurationStorage
) : DeviceInfo {

    private val displayMetrics: DisplayMetrics by lazy {
        context.resources.displayMetrics
    }

    override val deviceId: String
        get() = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) + ":" + context.packageName

    override val appVersionName: String
        get() = configurationStorage.appVersion

    override val osVersion: String
        get() = Build.VERSION.RELEASE

    override val model: String
        get() = Build.MODEL

    override val manufacturer: String
        get() = Build.MANUFACTURER

    override val screenWidthPixels: Int
        get() = displayMetrics.widthPixels

    override val screenHeightPixels: Int
        get() = displayMetrics.heightPixels

    override val isBluetoothEnabled: Boolean
        get() = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true

    override val isWifiOn: Boolean
        get() = context.isWifiOn

    override val isWifiConnected: Boolean
        get() = context.isWifiConnected
}