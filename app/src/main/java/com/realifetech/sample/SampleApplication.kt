package com.realifetech.sample

import android.app.Application
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.data.color.ColorType
import com.realifetech.sdk.core.data.config.CoreConfiguration

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val storage = DeviceConfigurationStorage(this)
        // Prefill the storage with default values from the configuration
        if (storage.graphQl.isBlank() && storage.apiUrl.isBlank() && storage.orderingJourney.isBlank()) {
            val configuration = CoreConfiguration("LS", "client_secrete")
            storage.graphQl = configuration.graphApiUrl
            storage.apiUrl = configuration.apiUrl
            storage.orderingJourney = configuration.webOrderingJourneyUrl
        }
        val configuration = CoreConfiguration(
            apiUrl = storage.apiUrl,
            graphApiUrl = storage.graphQl,
            clientSecret = storage.clientSecret,
            appCode = storage.appCode,
            webOrderingJourneyUrl = storage.orderingJourney
        )
        RealifeTech.configureSdk(this, configuration)

//  Set Colors via code
        // EXAMPLE 1
        RealifeTech.getGeneral().setColor(Color.parseColor("#012d4C"), ColorType.PRIMARY)
        //EXAMPLE 2
        RealifeTech.getGeneral()
            .setColor(ContextCompat.getColor(this, R.color.colorAccent), ColorType.ON_PRIMARY)
    }
}