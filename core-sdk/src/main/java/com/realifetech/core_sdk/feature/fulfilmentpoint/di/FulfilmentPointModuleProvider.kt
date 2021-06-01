package com.realifetech.core_sdk.feature.fulfilmentpoint.di

import android.content.Context
import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.core_sdk.feature.fulfilmentpoint.FulfilmentPointRepository
import com.realifetech.core_sdk.feature.fulfilmentpoint.data.FulfilmentPointBackendDataSource
import com.realifetech.core_sdk.feature.widgets.WidgetsRepository
import com.realifetech.core_sdk.feature.widgets.data.WidgetsBackendDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object FulfilmentPointModuleProvider {
    fun provideFulfilmentPointRepository(
        baseUrl: String,
        context: Context,
        deviceId: String
    ): FulfilmentPointRepository {
        CoreConfiguration.deviceId = deviceId
        val client = GraphQlModule.getApolloClient(baseUrl, context)
        return FulfilmentPointRepository(FulfilmentPointBackendDataSource(client))
    }
}