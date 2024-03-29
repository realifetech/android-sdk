package com.realifetech.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
                newDictionary,
                oldDictionary
            ) { error, result ->
                progressBar.isVisible = false
                result.takeIf { it }?.let {
                    resultTextView.text = "Success"
                }
                error?.let {
                    resultTextView.text = "Failure"
                }
            }


    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AnalyticsSampleActivity::class.java))
        }
    }
}
