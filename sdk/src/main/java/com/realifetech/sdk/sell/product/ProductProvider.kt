package com.realifetech.sdk.sell.product

import com.realifetech.core_sdk.feature.product.ProductRepository
import com.realifetech.core_sdk.feature.product.di.ProductModuleProvider
import com.realifetech.sdk.general.General

internal class ProductProvider {
    fun provideProductRepository(): ProductRepository {
        return ProductModuleProvider.provideProductRepository(General.instance.configuration.graphApiUrl)
    }
}