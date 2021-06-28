package com.realifetech.sdk.sell.order

import com.realifetech.core_sdk.feature.order.OrderRepository
import com.realifetech.core_sdk.feature.order.di.OrderModuleProvider

internal class OrderProvider {
    fun provideOrderRepository(): OrderRepository {
        return OrderModuleProvider.provideOrderRepository()
    }
}