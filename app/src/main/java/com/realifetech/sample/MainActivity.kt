package com.realifetech.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sample.webPage.WebPageSampleActivity
import com.realifetech.sample.widgets.WidgetsSampleActivity
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.sell.Sell
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appVersionTextView.text = BuildConfig.VERSION_NAME
        initTextViews()
        onTextViewsChanged()
        onClickButtons()
    }

    private fun onClickButtons() {
        generalButton.setOnClickListener { GeneralSampleActivity.start(this) }
        communicateButton.setOnClickListener { CommunicationSampleActivity.start(this) }
        analyticsButton.setOnClickListener { AnalyticsSampleActivity.start(this) }
        audienceButton.setOnClickListener { AudienceSampleActivity.start(this) }
        widgetsButton.setOnClickListener { WidgetsSampleActivity.start(this) }
        webPage.setOnClickListener { WebPageSampleActivity.start(this) }
        ordering_journey.setOnClickListener { Sell.createOrderingJourneyFragment() }
    }

    private fun onTextViewsChanged() {
        val storage = DeviceConfigurationStorage(this)
        apiUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.getGeneral().configuration.apiUrl = text.toString()
            storage.apiUrl = text.toString()
        }
        graphQlUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.getGeneral().configuration.graphApiUrl = text.toString()
            storage.graphQl = text.toString()
        }
        orderingUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.getGeneral().configuration.webOrderingJourneyUrl = text.toString()
            storage.orderingJourney = text.toString()
        }
    }

    private fun initTextViews() {
        toolbar.setTitle(R.string.app_name)
        apiUrlEditTextView.setText(RealifeTech.getGeneral().configuration.apiUrl)
        graphQlUrlEditTextView.setText(RealifeTech.getGeneral().configuration.graphApiUrl)
        orderingUrlEditTextView.setText(RealifeTech.getGeneral().configuration.webOrderingJourneyUrl)
    }
}
