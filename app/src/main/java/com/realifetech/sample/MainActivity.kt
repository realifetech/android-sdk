package com.realifetech.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sample.webPage.WebPageSampleActivity
import com.realifetech.sample.widgets.WidgetsSampleActivity
import com.realifetech.sdk.RealifeTech
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val storage = DeviceConfigurationStorage(this)
        appVersionTextView.text = BuildConfig.VERSION_NAME
        initTextViews(storage)
        onTextViewsChanged(storage)
        onClickButtons()
    }

    private fun onClickButtons() {
        generalButton.setOnClickListener { GeneralSampleActivity.start(this) }
        communicateButton.setOnClickListener { CommunicationSampleActivity.start(this) }
        analyticsButton.setOnClickListener { AnalyticsSampleActivity.start(this) }
        audienceButton.setOnClickListener { AudienceSampleActivity.start(this) }
        widgetsButton.setOnClickListener { WidgetsSampleActivity.start(this) }
        webPage.setOnClickListener { WebPageSampleActivity.start(this) }
        ordering_journey.setOnClickListener {
            RealifeTech.getSell().createOrderingJourneyFragment()
        }
    }

    private fun onTextViewsChanged(storage: DeviceConfigurationStorage) {
        apiUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.configuration.apiUrl = text.toString()
            storage.apiUrl = text.toString()
        }
        graphQlUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.configuration.graphQl = text.toString()
            storage.graphQl = text.toString()
        }
        orderingUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.set(text.toString())
            storage.orderingJourney = text.toString()
        }
    }

    private fun initTextViews(storage: DeviceConfigurationStorage) {
        toolbar.setTitle(R.string.app_name)
        apiUrlEditTextView.setText(storage.apiUrl)
        graphQlUrlEditTextView.setText(storage.graphQl)
        orderingUrlEditTextView.setText(storage.orderingJourney)
    }
}
