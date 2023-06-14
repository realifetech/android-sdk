package com.realifetech.sdk.communicate.domain

import com.realifetech.sdk.communicate.domain.model.DeviceNotificationConsent
import com.realifetech.sdk.communicate.domain.model.NotificationConsent
import com.realifetech.type.LocationGranular

interface NotificationConsentRepository {
    suspend fun getNotificationConsents(): List<NotificationConsent?>
    suspend fun getMyDeviceNotificationConsents(): List<DeviceNotificationConsent?>
    suspend fun updateMyDeviceNotificationConsent(id: String, enabled: Boolean): Boolean
    suspend fun updateMyDeviceConsent(
        calendar: Boolean,
        camera: Boolean,
        locationCapture: Boolean,
        locationGranular: LocationGranular,
        photoSharing: Boolean,
        pushNotification: Boolean
    ): Boolean
}