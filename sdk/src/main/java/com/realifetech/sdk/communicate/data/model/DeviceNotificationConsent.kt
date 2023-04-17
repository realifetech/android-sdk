package com.realifetech.sdk.communicate.data.model

data class DeviceNotificationConsent(
    val id: String,
    val enabled: Boolean,
    val consent: NotificationConsent
)

