package com.realifetech.sdk.sell.product.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.realifetech.GetProductByIdQuery
import com.realifetech.GetProductsQuery
import com.realifetech.sdk.core.data.model.product.Product
import com.realifetech.sdk.core.data.model.product.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.core.data.model.shared.`object`.asInput
import com.realifetech.type.ProductFilter
import kotlinx.coroutines.flow.singleOrNull
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    ProductDataSource {

    override suspend fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter,
        params: List<FilterParamWrapper>?
    ): PaginatedObject<Product?> {
        try {
            val query = GetProductsQuery(
                pageSize,
                Optional.Present(page),
                Optional.Present(filters),
                Optional.presentIfNotNull(params?.map { it.asInput })
            )
            val response = apolloClient.query(query).toFlow().singleOrNull()
            val products = response?.data?.getProducts?.edges?.mapNotNull { it?.fragmentProduct?.asModel }
            val nextPage = response?.data?.getProducts?.nextPage
            return PaginatedObject(products, nextPage)
        } catch (exception: ApolloHttpException) {
            throw Exception(exception)
        } catch (exception: ApolloException) {
            throw Exception(exception)
        }
    }

    override suspend fun getProductById(
        id: String,
        params: List<FilterParamWrapper>?
    ): Product? {
        try {
            val query = GetProductByIdQuery(
                id,
                Optional.presentIfNotNull(params?.map { it.asInput })
            )
            val response = apolloClient.query(query).toFlow().singleOrNull()
            return response?.data?.getProduct?.fragmentProduct?.asModel
        } catch (exception: ApolloHttpException) {
            throw Exception(exception)
        } catch (exception: ApolloException) {
            throw Exception(exception)
        }
    }}