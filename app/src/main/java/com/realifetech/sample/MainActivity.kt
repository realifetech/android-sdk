package com.realifetech.sample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.doOnTextChanged
import com.realifetech.sample.campaignAutomation.CampaignAutomationActivity
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sample.identity.IdentityActivity
import com.realifetech.sample.identity.IdentitySSOActivity
import com.realifetech.sample.webPage.WebPageSampleActivity
import com.realifetech.sample.widgets.WidgetsSampleActivity
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.campaignautomation.data.model.BannerDataModel
import com.realifetech.sdk.campaignautomation.data.model.RLTBannerFactory
import com.realifetech.sdk.campaignautomation.data.model.RLTCreatableFactory
import com.realifetech.sdk.campaignautomation.data.model.RLTViewCreatable
import com.realifetech.sdk.core.data.model.config.CoreConfiguration
import com.realifetechCa.type.ContentType
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.banner_view.view.*

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
        identityButton.setOnClickListener { IdentityActivity.start(this) }
        identitySSButton.setOnClickListener{ IdentitySSOActivity.start(this) }
        communicateButton.setOnClickListener { CommunicationSampleActivity.start(this) }
        analyticsButton.setOnClickListener { AnalyticsSampleActivity.start(this) }
        audienceButton.setOnClickListener { AudienceSampleActivity.start(this) }
        widgetsButton.setOnClickListener { WidgetsSampleActivity.start(this) }
        webPage.setOnClickListener { WebPageSampleActivity.start(this) }
        campaignAutomationButton.setOnClickListener { CampaignAutomationActivity.start(this) }
        ordering_journey.setOnClickListener {
            RealifeTech.getSell().createOrderingJourneyFragment()
        }
        identityButton.setOnClickListener { IdentityActivity.start(this) }
    }


    private fun onTextViewsChanged(storage: DeviceConfigurationStorage) {
        apiUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.configuration.apiUrl = text.toString()
            storage.apiUrl = text.toString()
            reconfigureSDK(storage)

        }
        graphQlUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.configuration.graphApiUrl = text.toString()
            storage.graphQl = text.toString()
            reconfigureSDK(storage)
        }
        orderingUrlEditTextView.doOnTextChanged { text, _, _, _ ->
            RealifeTech.set(text.toString())
            storage.orderingJourney = text.toString()
            reconfigureSDK(storage)
        }
    }

    private fun reconfigureSDK(storage: DeviceConfigurationStorage) {
        val configuration = CoreConfiguration(
            apiUrl = storage.apiUrl,
            graphApiUrl = storage.graphQl,
            clientSecret = storage.clientSecret,
            appCode = storage.appCode,
            webOrderingJourneyUrl = storage.orderingJourney
        )
        RealifeTech.configureSdk(applicationContext, configuration)

    }

    private fun initTextViews(storage: DeviceConfigurationStorage) {
        toolbar.setTitle(R.string.app_name)
        apiUrlEditTextView.setText(storage.apiUrl)
        graphQlUrlEditTextView.setText(storage.graphQl)
        orderingUrlEditTextView.setText(storage.orderingJourney)
    }
}
