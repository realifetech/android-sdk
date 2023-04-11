package com.realifetech.sdk.communicate.domain

import android.util.Log
import com.realifetech.sdk.communicate.data.PushConsentDataSource
import com.realifetech.sdk.communicate.data.model.DeviceNotificationConsent
import com.realifetech.sdk.communicate.data.model.NotificationConsent
import com.realifetech.sdk.core.utils.Result
import javax.inject.Inject

class PushConsentRepository @Inject constructor(private val dataSource: PushConsentDataSource) {
    private lateinit var result: Result<Boolean>

    fun getNotificationConsents(): List<NotificationConsent> {
        var notificationConsentList = emptyList<NotificationConsent>()
        dataSource.getNotificationConsents { error, response ->
            error?.let {
                Log.e("PushConsentRepository", "Get notification consents Error: ${it.message}")
            }
            response?.let {
                notificationConsentList = it.filterNotNull()
            }
        }
        return notificationConsentList
    }

    fun getMyNotificationConsents(): List<DeviceNotificationConsent> {
        var deviceNotificationConsentList = emptyList<DeviceNotificationConsent>()
        dataSource.getMyNotificationConsents { error, response ->
            error?.let {
                Log.e("PushConsentRepository", "Get my notification consents Error: ${it.message}")
            }
            response?.let {
                deviceNotificationConsentList = it.filterNotNull()
            }
        }
        return deviceNotificationConsentList
    }

    fun updateMyNotificationConsent(id: String, enabled: Boolean): Result<Boolean> {
        dataSource.updateMyNotificationConsent(id, enabled) { error, success ->
            error?.let {
                Log.e("PushConsentRepository", "Update my notification consents Error: ${it.message}")
                result = Result.Error(it)
            }
            success?.let {
                result = Result.Success(it)
            }
        }
        return result
    }
}