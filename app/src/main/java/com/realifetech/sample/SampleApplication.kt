package com.realifetech.sample

import android.app.Application
import com.realifetech.sample.data.DeviceConfigurationStorage
import com.realifetech.sdk.RealifeTech

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val storage = DeviceConfigurationStorage(this)

        RealifeTech.getGeneral().configuration.apply {
            context = this@SampleApplication
            apiUrl = storage.apiUrl
            graphApiUrl = storage.graphQl
            clientSecret = storage.clientSecret
        }
    }
}