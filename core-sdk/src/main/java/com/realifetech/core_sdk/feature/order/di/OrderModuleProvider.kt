package com.realifetech.core_sdk.feature.order.di

import android.content.Context
import com.realifetech.core_sdk.feature.order.OrderRepository
import com.realifetech.core_sdk.feature.order.data.OrderBackendDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object OrderModuleProvider {
    fun provideOrderRepository(baseUrl: String, context: Context): OrderRepository {
        val client = GraphQlModule.getApolloClient(baseUrl)
        return OrderRepository(OrderBackendDataSource(client), context)
    }
}