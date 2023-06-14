package com.realifetech.sample.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.realifetech.sample.service.FirebaseMessageListenerService.Companion.FAILED_MESSAGE
import com.realifetech.sample.service.FirebaseMessageListenerService.Companion.TRACKED_MESSAGE
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.communicate.data.Event
import kotlinx.coroutines.launch

abstract class SampleAppCompatActivity : AppCompatActivity() {

    companion object {
        const val IS_PN = "is_push_notification"
        const val PN_PAYLOAD = "push_notification_payload"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackPushNotification(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { trackPushNotification(it) }
    }

    private fun trackPushNotification(intent: Intent) {
        val arguments = intent.extras
        val isPN = arguments?.getBoolean(IS_PN, false)
        val payload = arguments?.getSerializable(PN_PAYLOAD) as? Map<*, *>
        payload?.let {
            if (isPN == true && it.isNotEmpty()) {
                val event = Event.OPENED
                val trackInfo = it as? Map<String, Any>
                trackPush(event, trackInfo)
            }
        }
    }

    private fun trackPush(event: Event, trackInfo: Map<String, Any>?) {
        lifecycleScope.launch {
            try {
                val result = RealifeTech.getCommunicate().trackPush(event, trackInfo)
                handleResponse(result)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun handleError(error: Exception?) {
        error?.let { Log.e(this.javaClass.name, "Error: ${error.message}") }
    }

    private fun handleResponse(response: Boolean) {
        val responseMessage = "Notification was opened" +
                if (response) TRACKED_MESSAGE else FAILED_MESSAGE
        Log.d(this.javaClass.name, responseMessage)
    }
}