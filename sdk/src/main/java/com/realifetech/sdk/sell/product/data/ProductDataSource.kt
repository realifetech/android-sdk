package com.realifetech.sdk.sell.product.data

import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.ProductFilter

interface ProductDataSource {
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