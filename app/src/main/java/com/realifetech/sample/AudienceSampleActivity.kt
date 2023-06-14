package com.realifetech.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.realifetech.sdk.RealifeTech
import kotlinx.android.synthetic.main.activity_analytics_sample.typeEditText
import kotlinx.android.synthetic.main.activity_audience_sample.*
import kotlinx.android.synthetic.main.activity_communication_sample.operationTextView
import kotlinx.android.synthetic.main.activity_communication_sample.progressBar
import kotlinx.android.synthetic.main.activity_communication_sample.resultTextView
import kotlinx.coroutines.launch

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

        val audience = RealifeTech.getAudience()
        lifecycleScope.launch {
            try {
                val doesBelong = audience.deviceIsMemberOfAudience(typeEditText.text.toString())
                progressBar.isVisible = false
                resultTextView.text = "Does belong to audience? Response = $doesBelong"
            } catch (e: Exception) {
                progressBar.isVisible = false
                resultTextView.text = e.message ?: "Unknown error"
                e.printStackTrace()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AudienceSampleActivity::class.java))
        }
    }
}