package com.realifetech.sdk.sell.product

import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.product.domain.ProductRepository
import com.realifetech.type.ProductFilter
import javax.inject.Inject

class ProductFeature @Inject constructor(private val productRepo: ProductRepository) {

    suspend fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter,
        params: List<FilterParamWrapper>?
    ): PaginatedObject<Product?> {
        return productRepo.getProducts(pageSize, page, filters, params)
    }

    suspend fun getProductById(
        id: String,
        params: List<FilterParamWrapper>?
    ): Product? {
        return productRepo.getProductById(id, params)
    }
}