package com.realifetech.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sample.webPage.WebPageSampleActivity
import com.realifetech.sample.widgets.WidgetsSampleActivity
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.domain.RLTConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
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
        ordering_journey.setOnClickListener {
            RealifeTech.getSell().createOrderingJourneyFragment()
        }
    }

    private fun onTextViewsChanged() {
        val storage = DeviceConfigurationStorage(this)
        apiUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RLTConfiguration.API_URL = text.toString()
            RLTConfiguration.API_URL = text.toString()
        }
        graphQlUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RLTConfiguration.GRAPHQL_URL = text.toString()
            storage.graphQl = text.toString()
        }
        orderingUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.set(text.toString())
            storage.orderingJourney = text.toString()
        }
    }

    private fun initTextViews() {
        toolbar.setTitle(R.string.app_name)
        apiUrlEditTextView.setText(RLTConfiguration.API_URL)
        graphQlUrlEditTextView.setText(RLTConfiguration.GRAPHQL_URL)
        orderingUrlEditTextView.setText(RLTConfiguration.ORDERING_JOURNEY_URL)
    }
}
