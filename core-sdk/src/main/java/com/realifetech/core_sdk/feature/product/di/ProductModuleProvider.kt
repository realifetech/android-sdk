package com.realifetech.core_sdk.feature.product.di

import com.realifetech.core_sdk.feature.product.ProductRepository
import com.realifetech.core_sdk.feature.product.data.ProductBackendDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object ProductModuleProvider {
    fun provideProductRepository(baseUrl: String): ProductRepository {
        val client = GraphQlModule.getApolloClient(baseUrl)
        return ProductRepository(ProductBackendDataSource(client))
    }
}