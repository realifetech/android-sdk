package com.realifetech.sdk.di.features.modules

import com.realifetech.sdk.core.database.order.OrdersSharedPreferencesManager
import com.realifetech.sdk.sell.basket.data.BasketDataSource
import com.realifetech.sdk.sell.basket.domain.BasketRepository
import com.realifetech.sdk.sell.fulfilmentpoint.data.FulfilmentPointBackendDataSource
import com.realifetech.sdk.sell.fulfilmentpoint.domain.FulfilmentPointRepository
import com.realifetech.sdk.sell.order.data.OrderBackendDataSource
import com.realifetech.sdk.sell.order.domain.OrderRepository
import com.realifetech.sdk.sell.payment.data.PaymentDataSource
import com.realifetech.sdk.sell.payment.domain.PaymentRepository
import com.realifetech.sdk.sell.product.data.ProductBackendDataSource
import com.realifetech.sdk.sell.product.domain.ProductRepository
import dagger.Module
import dagger.Provides


@Module
object SellModule {

    @Provides
    fun basketRepository(dataSource: BasketDataSource) = BasketRepository(dataSource)

    @Provides
    fun productRepository(dataSource: ProductBackendDataSource) = ProductRepository(dataSource)

    @Provides
    fun fulfilmentPointRepository(dataSource: FulfilmentPointBackendDataSource) =
        FulfilmentPointRepository(dataSource)

    @Provides
    fun paymentRepository(dataSource: PaymentDataSource) =
        PaymentRepository(dataSource)

    @Provides
    fun orderRepository(
        dataSource: OrderBackendDataSource,
        localStorageManager: OrdersSharedPreferencesManager
    ) = OrderRepository(dataSource, localStorageManager)

}