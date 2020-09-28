package com.realifetech.sdk.general.data

/**
 * It's used for extracting necessary information about the device, in order to register it.
 */
interface DeviceInfo {
    val deviceId: String
    val appVersionName: String
    val osVersion: String
    val model: String
    val manufacturer: String
    val screenWidthPixels: Int
    val screenHeightPixels: Int
    val isBluetoothEnabled: Boolean
    val isWifiOn: Boolean
    val isWifiConnected: Boolean
}