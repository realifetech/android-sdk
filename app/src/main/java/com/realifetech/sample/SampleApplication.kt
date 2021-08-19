package com.realifetech.sample

import android.app.Application
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.general.data.color.ColorType

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val storage = DeviceConfigurationStorage(this)

        // Prefill the storage with default values from the configuration
        if (storage.graphQl.isBlank() && storage.apiUrl.isBlank()) {
            storage.graphQl = RealifeTech.getGeneral().configuration.graphApiUrl
            storage.apiUrl = RealifeTech.getGeneral().configuration.apiUrl
        }

        RealifeTech.getGeneral().configuration.apply {
            context = this@SampleApplication
            apiUrl = storage.apiUrl
            graphApiUrl = storage.graphQl
            clientSecret = storage.clientSecret
            appCode = storage.clientId
        }
//  Set Colors via code
        // EXAMPLE 1
//        RealifeTech.getGeneral().setColor(Color.parseColor("#000"), ColorType.PRIMARY)
        //EXAMPLE 2
//        RealifeTech.getGeneral()
//            .setColor(ContextCompat.getColor(this, R.color.colorAccent), ColorType.ON_PRIMARY)
    }
}