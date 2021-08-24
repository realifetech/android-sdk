package com.realifetech.sdk.sell.order.di

import com.realifetech.sdk.sell.order.domain.OrderRepository
import com.realifetech.sdk.sell.order.data.OrderBackendDataSource

object OrderModuleProvider {
    fun provideOrderRepository(): OrderRepository {
        return OrderRepository(OrderBackendDataSource())
    }
}