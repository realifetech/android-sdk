package com.realifetech.sdk.sell.order

import com.realifetech.sdk.sell.order.domain.OrderRepository
import com.realifetech.sdk.sell.order.di.OrderModuleProvider

internal class OrderProvider {
    fun provideOrderRepository(): OrderRepository {
        return OrderModuleProvider.provideOrderRepository()
    }
}