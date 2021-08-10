package com.realifetech.core_sdk.feature.product

import com.realifetech.core_sdk.data.product.Product
import com.realifetech.core_sdk.data.shared.`object`.FilterParamWrapper
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.type.ProductFilter

class ProductRepository(private val dataSource: DataSource) {

    fun getProductById(
        id: String,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, product: Product?) -> Unit
    ) =
        dataSource.getProductById(id, params, callback)

    fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, response: PaginatedObject<Product?>?) -> Unit
    ) =
        dataSource.getProducts(pageSize, page, filters, params, callback)

    interface DataSource {
        fun getProducts(
            pageSize: Int,
            page: Int,
            filters: ProductFilter,
            params: List<FilterParamWrapper>?,
            callback: (error: Exception?, response: PaginatedObject<Product?>?) -> Unit
        )

        fun getProductById(
            id: String,
            params: List<FilterParamWrapper>?,
            callback: (error: Exception?, product: Product?) -> Unit
        )
    }
}