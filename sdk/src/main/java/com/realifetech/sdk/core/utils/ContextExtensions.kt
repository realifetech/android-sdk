package com.realifetech.sdk.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager


val Context.isWifiOn: Boolean
    get() {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

val Context.isWifiConnected: Boolean
    get() {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
    }

val Context.hasNetworkConnection: Boolean
    get() {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.activeNetworkInfo?.isConnected == true
    }