package com.realifetech.sample

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.realifetech.sdk.RealifeTech
import kotlinx.android.synthetic.main.activity_analytics_sample.*
import kotlinx.android.synthetic.main.activity_communication_sample.operationTextView
import kotlinx.android.synthetic.main.activity_communication_sample.progressBar
import kotlinx.android.synthetic.main.activity_communication_sample.resultTextView

class AnalyticsSampleActivity : AppCompatActivity() {

    private val oldDictionary: Map<String, String>?
        get() {
            return if (oldDictionaryCheckBox.isChecked) {
                mapOf("OldUserId" to "123")
            } else {
                null
            }
        }

    private val newDictionary: Map<String, String>?
        get() {
            return if (newDictionaryCheckBox.isChecked) {
                mapOf("NewUserId" to "278")
            } else {
                null
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics_sample)

        sendEventButton.setOnClickListener {
            sendEvent()
        }
    }

    private fun sendEvent() {
        progressBar.isVisible = true
        resultTextView.text = ""
        operationTextView.text = "Sending analytics event"

        RealifeTech.getAnalytics()
            .track(
                typeEditText.text.toString(),
                actionEditText.text.toString(),
                userEditText.text.toString(),
                newDictionary,
                oldDictionary) {
                progressBar.isVisible = false
                resultTextView.text = when (it != null) {
                    false -> "Success!"
                    else -> it?.message ?: "Unknown error"
                }
            }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AnalyticsSampleActivity::class.java))
        }
    }
}
