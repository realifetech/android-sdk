package com.realifetech.core_sdk.feature.screen.di

import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.core_sdk.feature.screen.ScreenRepository
import com.realifetech.core_sdk.feature.screen.data.ScreenBackendDataSource

object ScreenModuleProvider {
    fun provideScreenRepository(deviceId: String): ScreenRepository {
        CoreConfiguration.deviceId = deviceId
        return ScreenRepository(ScreenBackendDataSource())
    }
}