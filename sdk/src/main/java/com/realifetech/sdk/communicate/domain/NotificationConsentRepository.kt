package com.realifetech.sdk.communicate.domain

import com.realifetech.sdk.communicate.domain.model.DeviceNotificationConsent
import com.realifetech.sdk.communicate.domain.model.NotificationConsent
import com.realifetech.type.LocationGranular

interface NotificationConsentRepository {
    fun getNotificationConsents(callback: (error: Exception?, response: List<NotificationConsent?>?) -> Unit)
    fun getMyDeviceNotificationConsents(callback: (error: Exception?, response: List<DeviceNotificationConsent?>?) -> Unit)
    fun updateMyDeviceNotificationConsent(
        id: String,
        enabled: Boolean,
        callback: (error: Exception?, success: Boolean?) -> Unit
    )
    fun updateMyDeviceConsent(
        calendar: Boolean,
        camera: Boolean,
        locationCapture: Boolean,
        locationGranular: LocationGranular,
        photoSharing: Boolean,
        pushNotification: Boolean,
        callback: (error: Exception?, success: Boolean?) -> Unit
    )
}