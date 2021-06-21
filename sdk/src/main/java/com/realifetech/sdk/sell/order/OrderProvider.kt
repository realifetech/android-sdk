package com.realifetech.sdk.sell.order

import android.content.Context
import com.realifetech.core_sdk.feature.order.OrderRepository
import com.realifetech.core_sdk.feature.order.di.OrderModuleProvider
import com.realifetech.sdk.general.General

internal class OrderProvider {
    fun provideOrderRepository(context: Context): OrderRepository {
        return OrderModuleProvider.provideOrderRepository(
            General.instance.configuration.graphApiUrl,
            context
        )
    }
}