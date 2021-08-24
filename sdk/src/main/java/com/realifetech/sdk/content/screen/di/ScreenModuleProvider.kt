package com.realifetech.sdk.content.screen.di

import com.realifetech.sdk.content.screen.domain.ScreenRepository
import com.realifetech.sdk.content.screen.data.ScreenBackendDataSource
import com.realifetech.sdk.core.domain.CoreConfiguration

object ScreenModuleProvider {
    fun provideScreenRepository(deviceId: String): ScreenRepository {
        CoreConfiguration.deviceId = deviceId
        return ScreenRepository(ScreenBackendDataSource())
    }
}