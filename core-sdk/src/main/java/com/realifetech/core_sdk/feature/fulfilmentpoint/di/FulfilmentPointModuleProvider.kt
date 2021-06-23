package com.realifetech.core_sdk.feature.fulfilmentpoint.di

import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.core_sdk.feature.fulfilmentpoint.FulfilmentPointRepository
import com.realifetech.core_sdk.feature.fulfilmentpoint.data.FulfilmentPointBackendDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object FulfilmentPointModuleProvider {
    fun provideFulfilmentPointRepository(baseUrl: String): FulfilmentPointRepository {
        val client = GraphQlModule.getApolloClient(baseUrl, CoreConfiguration.context)
        return FulfilmentPointRepository(FulfilmentPointBackendDataSource(client))
    }
}