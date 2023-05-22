package com.realifetech.sdk.communicate.domain.model

data class DeviceNotificationConsent(
    val id: String,
    val enabled: Boolean,
    val consent: NotificationConsent
)