package com.realifetech.sdk.sell.product

import com.realifetech.sdk.sell.product.domain.ProductRepository
import com.realifetech.sdk.sell.product.di.ProductModuleProvider

internal class ProductProvider {
    fun provideProductRepository(): ProductRepository {
        return ProductModuleProvider.provideProductRepository()
    }
}