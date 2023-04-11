package com.realifetech.sdk.communicate.domain

import android.util.Log
import com.realifetech.sdk.communicate.data.PushConsentDataSource
import com.realifetech.sdk.communicate.data.model.DeviceNotificationConsent
import com.realifetech.sdk.communicate.data.model.NotificationConsent
import com.realifetech.sdk.core.utils.Result
import javax.inject.Inject

class PushConsentRepository @Inject constructor(private val dataSource: PushConsentDataSource) {
    private lateinit var result: Result<Boolean>
    private lateinit var notificationConsentResult: Result<List<NotificationConsent>>
    private lateinit var deviceNotificationConsentResult: Result<List<DeviceNotificationConsent>>

    fun getNotificationConsents(): Result<List<NotificationConsent>> {
        dataSource.getNotificationConsents { error, response ->
            error?.let {
                Log.e("PushConsentRepository", "Get notification consents Error: ${it.message}")
                notificationConsentResult = Result.Error(it)
            }
            response?.let {
                notificationConsentResult = Result.Success(it.filterNotNull())
            }
        }
        return notificationConsentResult
    }

    fun getMyNotificationConsents(): Result<List<DeviceNotificationConsent>> {
        dataSource.getMyNotificationConsents { error, response ->
            error?.let {
                Log.e("PushConsentRepository", "Get my notification consents Error: ${it.message}")
                deviceNotificationConsentResult= Result.Error(it)
            }
            response?.let {
                deviceNotificationConsentResult = Result.Success(it.filterNotNull())
            }
        }
        return deviceNotificationConsentResult
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