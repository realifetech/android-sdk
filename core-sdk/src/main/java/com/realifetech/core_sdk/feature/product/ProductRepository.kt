package com.realifetech.core_sdk.feature.product

import com.realifetech.core_sdk.data.product.Product
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.domain.Result
import com.realifetech.type.ProductFilter

class ProductRepository(private val dataSource: DataSource) {

    suspend fun getProductById(id: String) = dataSource.getProductById(id)

    suspend fun getProducts(pageSize: Int, page: Int, filters: ProductFilter) =
        dataSource.getProducts(pageSize, page, filters)

    interface DataSource {
        suspend fun getProducts(
            pageSize: Int,
            page: Int,
            filters: ProductFilter
        ): Result<PaginatedObject<Product?>>

        suspend fun getProductById(id: String): Result<Product>
    }
}