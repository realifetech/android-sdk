package com.realifetech.sdk.sell.product

import com.realifetech.core_sdk.feature.product.ProductRepository
import com.realifetech.core_sdk.feature.product.di.ProductModuleProvider

internal class ProductProvider {
    fun provideProductRepository(): ProductRepository {
        return ProductModuleProvider.provideProductRepository()
    }
}