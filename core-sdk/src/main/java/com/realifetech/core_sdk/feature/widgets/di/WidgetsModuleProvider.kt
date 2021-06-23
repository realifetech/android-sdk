package com.realifetech.core_sdk.feature.widgets.di

import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.core_sdk.feature.widgets.WidgetsRepository
import com.realifetech.core_sdk.feature.widgets.data.WidgetsBackendDataSource

object WidgetsModuleProvider {
    fun provideWidgetsRepository(deviceId: String): WidgetsRepository {
        CoreConfiguration.deviceId = deviceId
        return WidgetsRepository(WidgetsBackendDataSource())
    }
}