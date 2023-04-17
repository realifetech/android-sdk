package com.realifetech.sdk.communicate.data

import com.realifetech.sdk.communicate.data.model.DeviceNotificationConsent
import com.realifetech.sdk.communicate.data.model.NotificationConsent

interface PushConsentDataSource {

    fun getNotificationConsents(callback: (error: Exception?, response: List<NotificationConsent?>?) -> Unit)

    fun getMyNotificationConsents(callback: (error: Exception?, response: List<DeviceNotificationConsent?>?) -> Unit)

    fun updateMyNotificationConsent(id: String, enabled: Boolean, callback: (error: Exception?, success: Boolean?) -> Unit)
}