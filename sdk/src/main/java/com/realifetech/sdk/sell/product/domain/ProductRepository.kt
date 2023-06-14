package com.realifetech.sdk.sell.product.domain

import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.product.data.ProductDataSource
import com.realifetech.type.ProductFilter

class ProductRepository(private val dataSource: ProductDataSource) {

    suspend fun getProductById(
        id: String,
        params: List<FilterParamWrapper>?
    ): Product? {
        return dataSource.getProductById(id, params)
    }

    suspend fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter,
        params: List<FilterParamWrapper>?
    ): PaginatedObject<Product?> {
        return dataSource.getProducts(pageSize, page, filters, params)
    }
}