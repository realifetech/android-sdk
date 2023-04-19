package com.realifetech.sdk.communicate.domain

import android.util.Log
import com.realifetech.sdk.communicate.data.PushConsentDataSource
import com.realifetech.sdk.communicate.data.model.DeviceNotificationConsent
import com.realifetech.sdk.communicate.data.model.NotificationConsent
import com.realifetech.sdk.core.utils.Result
import javax.inject.Inject
import javax.security.auth.callback.Callback

class PushConsentRepository @Inject constructor(private val dataSource: PushConsentDataSource) {
    private lateinit var result: Result<Boolean>

    fun getNotificationConsents(callback: (error: Exception?, response: List<NotificationConsent?>?) -> Unit) {
        dataSource.getNotificationConsents(callback)
    }

    fun getMyNotificationConsents(callback: (error: Exception?, response: List<DeviceNotificationConsent?>?) -> Unit) {
        dataSource.getMyNotificationConsents(callback)
    }

    fun updateMyNotificationConsent(id: String, enabled: Boolean): Result<Boolean> {
        result = Result.Error(Exception("Unknown error"))
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