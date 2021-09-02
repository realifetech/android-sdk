package com.realifetech.sample

import android.app.Application
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.data.config.CoreConfiguration

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val storage = DeviceConfigurationStorage(this)

        val configuration = CoreConfiguration(
            apiUrl = storage.apiUrl,
            graphApiUrl = storage.graphQl,
            clientSecret = storage.clientSecret,
            appCode = storage.clientId,
            webOrderingJourneyUrl = storage.orderingJourney
        )
        // Prefill the storage with default values from the configuration
        if (storage.graphQl.isBlank() && storage.apiUrl.isBlank() && storage.orderingJourney.isBlank()) {
            storage.graphQl = configuration.graphApiUrl
            storage.apiUrl = configuration.apiUrl
            storage.orderingJourney = configuration.webOrderingJourneyUrl
        }
        RealifeTech.configureSdk(this, configuration)

//  Set Colors via code
        // EXAMPLE 1
//        RealifeTech.getGeneral().setColor(Color.parseColor("#000"), ColorType.PRIMARY)
        //EXAMPLE 2
//        RealifeTech.getGeneral()
//            .setColor(ContextCompat.getColor(this, R.color.colorAccent), ColorType.ON_PRIMARY)
    }
}