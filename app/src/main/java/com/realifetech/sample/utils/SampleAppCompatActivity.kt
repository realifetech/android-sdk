package com.realifetech.sample.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.realifetech.sample.utils.NotificationUtil.IS_PN
import com.realifetech.sample.utils.NotificationUtil.PN_PAYLOAD
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.communicate.data.Event

abstract class SampleAppCompatActivity : AppCompatActivity() {

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
        payload.let {
            if (isPN == true && it?.isEmpty() == false) {
                RealifeTech.getCommunicate()
                    .trackPush(Event.OPENED, it as? Map<String, Any>) { error, response ->
                        handleResponse(response)
                        handleError(error)
                    }
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

    companion object {
        const val TRACKED_MESSAGE = " and we tracked it successfully"
        const val FAILED_MESSAGE = "  but our tracking radar failed to spot it"
    }
}