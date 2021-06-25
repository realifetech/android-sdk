package com.realifetech.core_sdk.feature.product

import com.realifetech.core_sdk.data.product.Product
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.type.ProductFilter

class ProductRepository(private val dataSource: DataSource) {

    fun getProductById(id: String, callback: (error: Exception?, product: Product?) -> Unit) =
        dataSource.getProductById(id, callback)

    fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter,
        callback: (error: Exception?, response: PaginatedObject<Product?>?) -> Unit
    ) =
        dataSource.getProducts(pageSize, page, filters, callback)

    interface DataSource {
        fun getProducts(
            pageSize: Int,
            page: Int,
            filters: ProductFilter,
            callback: (error: Exception?, response: PaginatedObject<Product?>?) -> Unit
        )

        fun getProductById(id: String, callback: (error: Exception?, product: Product?) -> Unit)
    }
}