package com.realifetech.core_sdk.feature.product.di

import com.realifetech.core_sdk.feature.product.ProductRepository
import com.realifetech.core_sdk.feature.product.data.ProductBackendDataSource

object ProductModuleProvider {
    fun provideProductRepository(): ProductRepository {
        return ProductRepository(ProductBackendDataSource())
    }
}