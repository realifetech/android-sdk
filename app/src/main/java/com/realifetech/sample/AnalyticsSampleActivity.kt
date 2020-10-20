package com.realifetech.sample

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.Realifetech
import com.realifetech.sdk.domain.Result
import kotlinx.android.synthetic.main.activity_communication_sample.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnalyticsSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics_sample)

        val storage = DeviceConfigurationStorage(this)
        tokenEditText.setText(storage.pushNotificationsToken)

        tokenEditText.doOnTextChanged { text, _, _, _ ->
            storage.pushNotificationsToken = text.toString()
        }

        registerPushNotifications.setOnClickListener {
            registerPushNotifications()
        }
    }

    private fun registerPushNotifications() {
        progressBar.isVisible = true
        resultTextView.text = ""
        operationTextView.text = "Registering for push notifications"

        GlobalScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) {
                Realifetech.getCommunicate().registerForPushNotifications(tokenEditText.text.toString())
            }

            progressBar.isVisible = false
            resultTextView.text = when (result) {
                is Result.Success -> "Success!"
                is Result.Error -> result.exception.message ?: "Unknown error"
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AnalyticsSampleActivity::class.java))
        }
    }
}
