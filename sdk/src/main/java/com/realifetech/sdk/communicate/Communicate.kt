package com.realifetech.sdk.communicate

import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.*
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

    fun trackPush(
        event: Event,
        trackInfo: Map<String, Any>?,
        completion: (error: Exception?, response: Boolean) -> Unit
    ) {
        analytics.track(
            USER,
            event.message,
            trackInfo,
            null,
            completion
        )
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
                    is Result.Success -> Log.d(
                        this.javaClass.name,
                        "Success while sending register for PN"
                    )
                    is Result.Error ->
                        Log.e(this.javaClass.name, "Error: ${result.exception.message}")
                }
            }
        } else return
    }

    fun getNotificationConsent(callback: (error: Exception?, response: List<NotificationConsent?>?) -> Unit) {
        notificationConsentRepository.getNotificationConsents(callback)
    }

    fun getMyDeviceNotificationConsents(callback: (error: Exception?, response: List<DeviceNotificationConsent?>?) -> Unit) {
        notificationConsentRepository.getMyDeviceNotificationConsents(callback)
    }

    fun updateMyDeviceNotificationConsent(id: String, enabled: Boolean, callback: (error: Exception?, success: Boolean?) -> Unit){
        notificationConsentRepository.updateMyDeviceNotificationConsent(id, enabled, callback)
    }

    companion object {
        private const val ID = "me"
        private const val GOOGLE = "GOOGLE"
        private const val USER = "user"
    }
}