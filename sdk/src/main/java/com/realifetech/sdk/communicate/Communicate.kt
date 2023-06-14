package com.realifetech.sdk.communicate

import android.content.Context
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.communicate.data.Event
import com.realifetech.sdk.communicate.data.TokenBody
import com.realifetech.sdk.communicate.domain.NotificationConsentRepository
import com.realifetech.sdk.communicate.domain.PushNotificationsTokenStorage
import com.realifetech.sdk.communicate.domain.model.DeviceNotificationConsent
import com.realifetech.sdk.communicate.domain.model.NotificationConsent
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.core.utils.hasNetworkConnection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class Communicate @Inject constructor(
    private val tokenStorage: PushNotificationsTokenStorage,
    private val realifetechApiV3Service: RealifetechApiV3Service,
    private val dispatcherIO: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher,
    private val analytics: Analytics,
    private val context: Context,
    private val notificationConsentRepository: NotificationConsentRepository
) {
    suspend fun trackPush(event: Event, trackInfo: Map<String, Any>?): Boolean {
        return try {
            val result = analytics.track(USER, event.message, trackInfo, null)
            // Log the successful result with Timber
            Timber.i("Push tracked successfully: $result")
            result
        } catch (e: Exception) {
            // Log the error with Timber
            Timber.e(e, "Error tracking push")
            throw e
        }
    }

    fun registerForPushNotifications(token: String): Result<Boolean> {
        tokenStorage.pendingToken = token

        if (!context.hasNetworkConnection) {
            return Result.Error(java.lang.RuntimeException("No Internet connection"))
        }

        val response =
            realifetechApiV3Service.pushNotifications(ID, TokenBody(GOOGLE, token))
                .execute()
        return if (response.isSuccessful) {
            tokenStorage.removePendingToken()
            Result.Success(true)
        } else {
            Result.Error(RuntimeException(response.errorBody()?.string()))
        }
    }

    internal fun resendPendingToken() {
        if (tokenStorage.hasPendingToken) {
            CoroutineScope(dispatcherMain).launch {
                val result = withContext(dispatcherIO) {
                    registerForPushNotifications(tokenStorage.pendingToken)
                }
                when (result) {
                    is Result.Success -> Timber.d("Success while sending register for PN")
                    is Result.Error -> Timber.e(result.exception, "Error while sending register for PN")
                }
            }
        } else return
    }

    suspend fun getNotificationConsent(): List<NotificationConsent?> {
        return try {
            notificationConsentRepository.getNotificationConsents()
        } catch (e: Exception) {
            Timber.e(e, "Failed to get notification consents")
            emptyList()
        }
    }

    suspend fun getMyDeviceNotificationConsents(): List<DeviceNotificationConsent?> {
        return try {
            notificationConsentRepository.getMyDeviceNotificationConsents()
        } catch (e: Exception) {
            Timber.e(e, "Failed to get my device notification consents")
            emptyList()
        }
    }

    suspend fun updateMyDeviceNotificationConsent(id: String, enabled: Boolean): Boolean {
        return try {
            notificationConsentRepository.updateMyDeviceNotificationConsent(id, enabled)
        } catch (e: Exception) {
            Timber.e(e, "Failed to update my device notification consent")
            false
        }
    }

    companion object {
        private const val ID = "me"
        private const val GOOGLE = "GOOGLE"
        private const val USER = "user"
    }
}