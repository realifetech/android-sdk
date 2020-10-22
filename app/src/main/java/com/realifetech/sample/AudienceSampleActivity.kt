package com.realifetech.sample

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.realifetech.sdk.RealifeTech
import kotlinx.android.synthetic.main.activity_analytics_sample.*
import kotlinx.android.synthetic.main.activity_analytics_sample.sendEventButton
import kotlinx.android.synthetic.main.activity_analytics_sample.typeEditText
import kotlinx.android.synthetic.main.activity_audience_sample.*
import kotlinx.android.synthetic.main.activity_communication_sample.operationTextView
import kotlinx.android.synthetic.main.activity_communication_sample.progressBar
import kotlinx.android.synthetic.main.activity_communication_sample.resultTextView

class AudienceSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audience_sample)

        queryAudienceButton.setOnClickListener { queryForAudience() }
    }

    private fun queryForAudience() {
        progressBar.isVisible = true
        resultTextView.text = ""
        operationTextView.text = "Query if belongs to audience"

        RealifeTech.getAudience().deviceIsMemberOfAudience(typeEditText.text.toString()) { error, doesBelong ->
            progressBar.isVisible = false
            resultTextView.text = when (error != null) {
                false -> "Does belong to audience? Response = $doesBelong"
                else -> error.message ?: "Unknown error"
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AudienceSampleActivity::class.java))
        }
    }
}
