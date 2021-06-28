package com.realifetech.core_sdk.feature.fulfilmentpoint.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetFulfilmentPointByIdQuery
import com.realifetech.GetFulfilmentPointCategoriesQuery
import com.realifetech.GetFulfilmentPointCategoryByIdQuery
import com.realifetech.GetFulfilmentPointsQuery
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.core_sdk.data.fulfilmentPoint.asModel
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.fulfilmentpoint.FulfilmentPointRepository
import com.realifetech.core_sdk.feature.helper.extractResponse
import com.realifetech.core_sdk.network.graphQl.GraphQlModule
import com.realifetech.type.FulfilmentPointFilter

class FulfilmentPointBackendDataSource() :
    FulfilmentPointRepository.DataSource {

    private val apolloClient = GraphQlModule.apolloClient

    override fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: Input<FulfilmentPointFilter>?,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPoint?>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(
                GetFulfilmentPointsQuery(
                    pageSize = pageSize,
                    page = page.toInput(),
                    filters = filters ?: FulfilmentPointFilter().toInput()
                )
            )
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetFulfilmentPointsQuery.Data>() {
                override fun onResponse(response: Response<GetFulfilmentPointsQuery.Data>) {
                    val fulfilmentPoints =
                        response.data?.getFulfilmentPoints?.edges?.map { result -> result?.fragments?.fragmentFulfilmentPoint?.asModel }
                    val nextPage = response.data?.getFulfilmentPoints?.nextPage
                    val paginatedObject = PaginatedObject(fulfilmentPoints, nextPage)
                    paginatedObject.extractResponse(response.errors)
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

    override fun getFulfilmentPointById(
        id: String,
        callback: (error: Exception?, fulfilmentPoint: FulfilmentPoint?) -> Unit
    ) {
        try {
            val response =
                apolloClient.query(GetFulfilmentPointByIdQuery(id))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetFulfilmentPointByIdQuery.Data>() {
                override fun onResponse(response: Response<GetFulfilmentPointByIdQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getFulfilmentPoint?.fragments?.fragmentFulfilmentPoint?.asModel
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

    override fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPointCategory?>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(
                GetFulfilmentPointCategoriesQuery(
                    pageSize = pageSize,
                    page = page.toInput()
                )
            )
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object :
                ApolloCall.Callback<GetFulfilmentPointCategoriesQuery.Data>() {
                override fun onResponse(response: Response<GetFulfilmentPointCategoriesQuery.Data>) {
                    val fulfilmentPointCategories =
                        response.data?.getFulfilmentPointCategories?.edges?.map { result -> result?.fragments?.fragmentFulfilmentPointCategory?.asModel }
                    val nextPage = response.data?.getFulfilmentPointCategories?.nextPage
                    val paginatedObject = PaginatedObject(fulfilmentPointCategories, nextPage)
                    paginatedObject.extractResponse(response.errors)
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

    override fun getFulfilmentPointCategoryById(
        id: String,
        callback: (error: Exception?, fulfilmentPointCategory: FulfilmentPointCategory?) -> Unit
    ) {
        try {
            val response = apolloClient.query(
                GetFulfilmentPointCategoryByIdQuery(id)
            )
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object :
                ApolloCall.Callback<GetFulfilmentPointCategoryByIdQuery.Data>() {
                override fun onResponse(response: Response<GetFulfilmentPointCategoryByIdQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getFulfilmentPointCategory?.fragments?.fragmentFulfilmentPointCategory?.asModel
                    )
                }

                override fun onFailure(e: ApolloException) {
                    callback(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }
}