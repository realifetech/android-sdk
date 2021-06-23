package com.realifetech.core_sdk.feature.product.di

import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.core_sdk.feature.product.ProductRepository
import com.realifetech.core_sdk.feature.product.data.ProductBackendDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object ProductModuleProvider {
    fun provideProductRepository(baseUrl: String): ProductRepository {
        val client = GraphQlModule.getApolloClient(baseUrl, CoreConfiguration.context)
        return ProductRepository(ProductBackendDataSource(client))
    }
}