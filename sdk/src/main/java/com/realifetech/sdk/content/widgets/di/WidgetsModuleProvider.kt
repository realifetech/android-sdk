package com.realifetech.sdk.content.widgets.di

import com.realifetech.sdk.core.domain.CoreConfiguration
import com.realifetech.sdk.content.widgets.domain.WidgetsRepository
import com.realifetech.sdk.content.widgets.data.WidgetsBackendDataSource

object WidgetsModuleProvider {
    fun provideWidgetsRepository(deviceId: String): WidgetsRepository {
        CoreConfiguration.deviceId = deviceId
        return WidgetsRepository(WidgetsBackendDataSource())
    }
}