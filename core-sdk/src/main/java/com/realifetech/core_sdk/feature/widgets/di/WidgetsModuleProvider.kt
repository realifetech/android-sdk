package com.realifetech.core_sdk.feature.widgets.di

import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.core_sdk.feature.widgets.WidgetsRepository
import com.realifetech.core_sdk.feature.widgets.data.WidgetsBackendDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object WidgetsModuleProvider {
    fun provideWidgetsRepository(baseUrl: String, deviceId: String): WidgetsRepository {
        CoreConfiguration.deviceId = deviceId
        val client = GraphQlModule.getApolloClient(baseUrl)
        return WidgetsRepository(WidgetsBackendDataSource(client))
    }
}