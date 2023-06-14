package com.realifetech.sdk.sell.product.data

import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.ProductFilter

interface ProductDataSource {
    suspend fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter,
        params: List<FilterParamWrapper>?
    ): PaginatedObject<Product?>

    suspend fun getProductById(
        id: String,
        params: List<FilterParamWrapper>?
    ): Product?
}