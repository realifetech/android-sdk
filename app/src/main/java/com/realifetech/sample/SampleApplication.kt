package com.realifetech.sample

import android.app.Application
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.core.data.model.config.CoreConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val storage = DeviceConfigurationStorage(this)
        storage.appVersion = BuildConfig.VERSION_NAME
        // Prefill the storage with default values from the configuration
        if (storage.graphQl.isBlank() && storage.apiUrl.isBlank() && storage.orderingJourney.isBlank()) {
            val configuration = CoreConfiguration()
            storage.graphQl = configuration.graphApiUrl
            storage.apiUrl = configuration.apiUrl
            storage.appCode = configuration.appCode
            storage.clientSecret = configuration.clientSecret
            storage.orderingJourney = configuration.webOrderingJourneyUrl
        }
        val configuration = CoreConfiguration(
            apiUrl = storage.apiUrl,
            appVersion = storage.appVersion,
            graphApiUrl = storage.graphQl,
            clientSecret = storage.clientSecret,
            appCode = storage.appCode,
            webOrderingJourneyUrl = storage.orderingJourney
        )
        RealifeTech.configureSdk(this, configuration)
        registerDeviceForSDK()

//  Set Colors via code
        // EXAMPLE 1
//        RealifeTech.getGeneral().setColor(Color.parseColor("#012d4C"), ColorType.PRIMARY)
        //EXAMPLE 2
//        RealifeTech.getGeneral()
//            .setColor(ContextCompat.getColor(this, R.color.colorAccent), ColorType.ON_PRIMARY)
    }

    private fun registerDeviceForSDK() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                RealifeTech.getGeneral().registerDevice()
            }
        }
    }

    companion object {
        private const val EMPTY = ""
    }
}