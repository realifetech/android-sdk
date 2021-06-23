package com.realifetech.core_sdk.feature.product.data

import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetProductByIdQuery
import com.realifetech.GetProductsQuery
import com.realifetech.core_sdk.data.product.Product
import com.realifetech.core_sdk.data.product.asModel
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.helper.extractResponse
import com.realifetech.core_sdk.feature.product.ProductRepository
import com.realifetech.core_sdk.network.graphQl.GraphQlModule
import com.realifetech.type.ProductFilter

class ProductBackendDataSource() :
    ProductRepository.DataSource {
    private val apolloClient = GraphQlModule.apolloClient

    override suspend fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter
    ): Result<PaginatedObject<Product?>> {
        return try {
            val response =
                apolloClient.query(GetProductsQuery(pageSize, page.toInput(), filters.toInput()))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                    .build()
                    .await()
            val products =
                response.data?.getProducts?.edges?.map { result -> result?.fragments?.fragmentProduct?.asModel }
            val nextPage = response.data?.getProducts?.nextPage
            val paginatedObject = PaginatedObject(products, nextPage)
            return paginatedObject.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getProductById(id: String): Result<Product> {
        return try {
            val response =
                apolloClient.query(GetProductByIdQuery(id))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                    .build()
                    .await()
            response.data?.getProduct?.fragments?.fragmentProduct?.asModel
                .extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

}