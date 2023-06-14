package com.realifetech.sample.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.realifetech.sample.R
import com.realifetech.sample.utils.CustomNotificationBody
import com.realifetech.sample.utils.NotificationUtil
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirebaseMessageListenerService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = withContext(Dispatchers.IO) {
                RealifeTech.getCommunicate().registerForPushNotifications(token)
            }
            when (result) {
                is Result.Success -> Log.d(
                    this.javaClass.name,
                    "Success while sending register for PN"
                )
                is Result.Error -> handleError(result.exception)

            }
        }
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val title = data[KEY_TITLE]
        var message = data[KEY_MESSAGE]
        var actionUrl = data[KEY_URL]
        val customData = data[KEY_CUSTOM]
        val trackData = data[KEY_TRACK_INFO]
        val referenceId = data[REFERENCE_ID]
        val trackInfo = hashMapOf<String, Any>()
        val gson = Gson()

        if (message.isNullOrEmpty()) {
            message = data[KEY_ALERT]
        }

        customData?.let {
            val (url) = gson.fromJson(customData, CustomNotificationBody::class.java)
            actionUrl = url
        }

        trackData?.let {
            val type = object : TypeToken<Map<String, Any>?>() {}.type
            val parsedTrackData: Map<String, Any>? = gson.fromJson(it, type)
            parsedTrackData?.let { trackInfo.putAll(it) }
        }

        showNotification(title, message, actionUrl, referenceId, trackInfo)
    }

    private fun handleError(error: Exception?) {
        error?.let { Log.e(this.javaClass.name, "Error: ${error.message}") }
    }

    private fun handleResponse(response: Boolean) {
        val responseMessage = "Notification was received" +
                if (response) TRACKED_MESSAGE else FAILED_MESSAGE
        Log.d(this.javaClass.name, responseMessage)
    }

    private fun showNotification(
        title: String?,
        message: String?,
        action: String?,
        referenceId: String?,
        trackInfo: HashMap<String, Any>
    ) {
        val escapedTitle =
            if (title.isNullOrEmpty()) getString(R.string.app_name) else title
        NotificationUtil.showNotification(
            this,
            escapedTitle,
            message,
            action,
            referenceId,
            trackInfo
        )
    }

    companion object {
        const val REFERENCE_ID = "reference_id"
        const val NOTIFICATION_TITLE_EXTRA = "notification_title_extra"
        const val KEY_TITLE = "title"
        const val KEY_ALERT = "alert"
        const val KEY_MESSAGE = "message"
        const val KEY_TRACK_INFO = "track"
        const val KEY_URL = "url"
        const val KEY_CUSTOM = "custom"
        const val TRACKED_MESSAGE = " and tracked successfully"
        const val FAILED_MESSAGE = " but we failed to track it"
    }
}