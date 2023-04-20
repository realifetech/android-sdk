package com.realifetech.sdk.communicate.data

import com.realifetech.sdk.communicate.domain.callbacks.NotificationConsentCallback

interface NotificationConsentRepository {
    suspend fun getNotificationConsents(callback: NotificationConsentCallback)
}