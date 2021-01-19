package com.realifetech.core_sdk.feature.widgets.di

import android.content.Context
import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.core_sdk.feature.widgets.WidgetsRepository
import com.realifetech.core_sdk.feature.widgets.data.WidgetsBackendDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object WidgetsModuleProvider {
    fun provideWidgetsRepository(
        baseUrl: String,
        context: Context,
        deviceId: String
    ): WidgetsRepository {
        CoreConfiguration.deviceId = deviceId
        val client = GraphQlModule.getApolloClient(baseUrl, context)
        return WidgetsRepository(WidgetsBackendDataSource(client))
    }
}