package com.realifetech.sdk.sell.product

import com.realifetech.core_sdk.data.product.Product
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.sdk.general.utils.executeCallback
import com.realifetech.type.ProductFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductFeature private constructor() {
    private val productRepo = ProductProvider().provideProductRepository()

    fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter,
        callback: (error: Exception?, response: PaginatedObject<Product?>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = productRepo.getProducts(pageSize, page, filters)
            executeCallback(result, callback)
        }
    }

    fun getProductById(
        id: String,
        callback: (error: Exception?, product: Product?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = productRepo.getProductById(id)
            executeCallback(result, callback)
        }
    }

    private object Holder {
        val instance = ProductFeature()
    }

    companion object {
        val INSTANCE: ProductFeature by lazy { Holder.instance }
    }
}