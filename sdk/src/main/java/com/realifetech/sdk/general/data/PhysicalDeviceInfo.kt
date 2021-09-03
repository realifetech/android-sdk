package com.realifetech.sdk.general.data

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.realifetech.realifetech_sdk.BuildConfig
import com.realifetech.sdk.general.utils.isWifiConnected
import com.realifetech.sdk.general.utils.isWifiOn
import javax.inject.Inject

/**
 * Returns information about the current device & based on the passed [Context]
 */
open class PhysicalDeviceInfo @Inject constructor(
    private val context: Context
) : DeviceInfo {

    private val displayMetrics: DisplayMetrics by lazy {
        context.resources.displayMetrics
    }

    override val deviceId: String
        get() = AdvertisingIdClient.getAdvertisingIdInfo(context).id + ":" + context.packageName

    override val appVersionName: String
        get() = "SDK_${BuildConfig.LIBRARY_PACKAGE_NAME}"

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