package com.realifetech.sample.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.realifetech.sample.R
import com.realifetech.sample.utils.CustomNotificationBody
import com.realifetech.sample.utils.NotificationUtil
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirebaseMessageListenerService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                RealifeTech.getCommunicate().registerForPushNotifications(token)
            }
            when (result) {
                is Result.Success -> Log.d(
                    this.javaClass.name,
                    "Success while sending register for PN"
                )
                is Result.Error -> Log.e(
                    this.javaClass.name,
                    "Error: ${result.exception.message}"
                )

            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        val title = data[KEY_TITLE]
        var message = data[KEY_MESSAGE]
        var actionUrl = data[KEY_URL]
        val customData = data[KEY_CUSTOM]
        val referenceId = data[REFERENCE_ID]
        if (message.isNullOrEmpty()) {
            message = data[KEY_ALERT]
        }
        if (customData.isNullOrEmpty()) {
            val gson = Gson()
            val (url) = gson.fromJson(
                customData,
                CustomNotificationBody::class.java
            )
            actionUrl = url
        }

        showNotification(title, message, actionUrl, referenceId)

    }

    private fun showNotification(
        title: String?,
        message: String?,
        action: String?,
        referenceId: String?
    ) {
        val escapedTitle =
            if (title.isNullOrEmpty()) getString(R.string.app_name) else title
        NotificationUtil().showNotification(this, escapedTitle, message, action, referenceId)
    }

    companion object {
        const val REFERENCE_ID = "reference_id"
        const val NOTIFICATION_TITLE_EXTRA = "notification_title_extra"
        const val KEY_TITLE = "title"
        const val KEY_ALERT = "alert"
        const val KEY_MESSAGE = "message"
        const val KEY_URL = "url"
        const val KEY_CUSTOM = "custom"
    }
}