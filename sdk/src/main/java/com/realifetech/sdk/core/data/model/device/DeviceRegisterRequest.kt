package com.realifetech.sdk.core.data.model.device

data class DeviceRegisterRequest(
    val token: String,
    val type: String,
    val appVersion: String,
    val osVersion: String,
    val model: String,
    val manufacturer: String,
    val screenWidth: Int,
    val screenHeight: Int,
    val bluetoothOn: Boolean,
    val wifiOn: Boolean,
    val wifiConnected: Boolean
)