package com.realifetech.sdk.sell.product

import com.realifetech.core_sdk.data.product.Product
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.type.ProductFilter

class ProductFeature private constructor() {
    private val productRepo = ProductProvider().provideProductRepository()

    fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter,
        callback: (error: Exception?, response: PaginatedObject<Product?>?) -> Unit
    ) {
        productRepo.getProducts(pageSize, page, filters, callback)
    }

    fun getProductById(
        id: String,
        callback: (error: Exception?, product: Product?) -> Unit
    ) {
        productRepo.getProductById(id, callback)
    }

    private object Holder {
        val instance = ProductFeature()
    }

    companion object {
        val INSTANCE: ProductFeature by lazy { Holder.instance }
    }
}