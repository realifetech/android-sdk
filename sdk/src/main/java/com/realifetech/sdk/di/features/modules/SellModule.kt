package com.realifetech.sdk.di.features.modules

import com.realifetech.sdk.sell.basket.data.BasketDataSourceImpl
import com.realifetech.sdk.sell.basket.domain.BasketRepository
import com.realifetech.sdk.sell.fulfilmentpoint.data.FulfilmentPointDataSourceImpl
import com.realifetech.sdk.sell.fulfilmentpoint.domain.FulfilmentPointRepository
import com.realifetech.sdk.sell.order.data.datasource.OrderBackendDataSource
import com.realifetech.sdk.sell.order.domain.OrderRepository
import com.realifetech.sdk.sell.payment.data.PaymentDataSourceImpl
import com.realifetech.sdk.sell.payment.domain.PaymentRepository
import com.realifetech.sdk.sell.product.data.ProductDataSourceImpl
import com.realifetech.sdk.sell.product.domain.ProductRepository
import dagger.Module
import dagger.Provides


@Module
object SellModule {

    @Provides
    fun basketRepository(dataSource: BasketDataSourceImpl) = BasketRepository(dataSource)

    @Provides
    fun productRepository(dataSource: ProductDataSourceImpl) = ProductRepository(dataSource)

    @Provides
    fun fulfilmentPointRepository(dataSource: FulfilmentPointDataSourceImpl) =
        FulfilmentPointRepository(dataSource)

    @Provides
    fun paymentRepository(dataSource: PaymentDataSourceImpl) =
        PaymentRepository(dataSource)

    @Provides
    fun orderRepository(
        dataSource: OrderBackendDataSource
    ) = OrderRepository(dataSource)

}