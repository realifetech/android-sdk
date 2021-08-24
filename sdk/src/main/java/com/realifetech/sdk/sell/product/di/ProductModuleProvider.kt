package com.realifetech.sdk.sell.product.di

import com.realifetech.sdk.sell.product.domain.ProductRepository
import com.realifetech.sdk.sell.product.data.ProductBackendDataSource

object ProductModuleProvider {
    fun provideProductRepository(): ProductRepository {
        return ProductRepository(ProductBackendDataSource())
    }
}