package com.realifetech.sdk.sell.product.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetProductByIdQuery
import com.realifetech.GetProductsQuery
import com.realifetech.sdk.core.data.product.Product
import com.realifetech.sdk.core.data.product.asModel
import com.realifetech.sdk.core.data.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.shared.`object`.PaginatedObject
import com.realifetech.sdk.core.data.shared.`object`.asInput
import com.realifetech.sdk.sell.product.domain.ProductRepository
import com.realifetech.sdk.core.network.graphQl.GraphQlModule
import com.realifetech.type.ProductFilter

class ProductBackendDataSource() :
    ProductRepository.DataSource {
    private val apolloClient = GraphQlModule.apolloClient

    override fun getProducts(
        pageSize: Int,
        page: Int,
        filters: ProductFilter,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, response: PaginatedObject<Product?>?) -> Unit
    ) {
        try {
            val response =
                apolloClient.query(
                    GetProductsQuery(
                        pageSize,
                        page.toInput(),
                        filters.toInput(),
                        Input.optional(params?.map { it.asInput })
                    )
                )
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetProductsQuery.Data>() {
                override fun onResponse(response: Response<GetProductsQuery.Data>) {
                    val products =
                        response.data?.getProducts?.edges?.map { result -> result?.fragments?.fragmentProduct?.asModel }
                    val nextPage = response.data?.getProducts?.nextPage
                    val paginatedObject = PaginatedObject(products, nextPage)
                    callback.invoke(null, paginatedObject)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getProductById(
        id: String,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, product: Product?) -> Unit
    ) {
        try {
            val response =
                apolloClient.query(
                    GetProductByIdQuery(
                        id,
                        Input.optional(params?.map { it.asInput })
                    )
                )
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetProductByIdQuery.Data>() {
                override fun onResponse(response: Response<GetProductByIdQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getProduct?.fragments?.fragmentProduct?.asModel
                    )
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

}