package com.realifetech.sdk.general.data

import com.realifetech.type.LocationGranular

data class DeviceConsent(
    val calendar: Boolean,
    val camera: Boolean,
    val locationCapture: Boolean,
    val locationGranular: LocationGranularStatus,
    val photoSharing: Boolean,
    val pushNotification: Boolean
)

enum class LocationGranularStatus {
    ALWAYS, APPINUSE
}

fun getLocationGranular(status: String): LocationGranular {
    return if (status == LocationGranularStatus.ALWAYS.toString()) {
        LocationGranular.ALWAYS
    } else {
        LocationGranular.APPINUSE
    }
}