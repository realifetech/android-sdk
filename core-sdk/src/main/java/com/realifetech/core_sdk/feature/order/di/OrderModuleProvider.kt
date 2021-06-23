package com.realifetech.core_sdk.feature.order.di

import com.realifetech.core_sdk.feature.order.OrderRepository
import com.realifetech.core_sdk.feature.order.data.OrderBackendDataSource

object OrderModuleProvider {
    fun provideOrderRepository(): OrderRepository {
        return OrderRepository(OrderBackendDataSource())
    }
}