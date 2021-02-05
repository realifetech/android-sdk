package com.realifetech.core_sdk.feature.screen.di

import android.content.Context
import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.core_sdk.feature.screen.ScreenRepository
import com.realifetech.core_sdk.feature.screen.data.ScreenBackendDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object ScreenModuleProvider {
    fun provideScreenRepository(
        baseUrl: String,
        deviceId: String,
        context: Context, ): ScreenRepository {
        CoreConfiguration.deviceId = deviceId
        val client = GraphQlModule.getApolloClient(baseUrl,context)
        return ScreenRepository(ScreenBackendDataSource(client))
    }
}