package com.realifetech.sdk.sell.fulfilmentpoint.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
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
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.fulfilmentPoint.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.core.data.model.shared.`object`.asInput
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.core.utils.extractResponse
import com.realifetech.sdk.core.utils.invokeCallback
import com.realifetech.type.FulfilmentPointFilter
import javax.inject.Inject

class FulfilmentPointDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    FulfilmentPointDataSource {

    override fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: Input<FulfilmentPointFilter>?,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPoint?>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(
                GetFulfilmentPointsQuery(
                    pageSize = pageSize,
                    page = page.toInput(),
                    filters = filters ?: FulfilmentPointFilter().toInput(),
                    params = Input.optional(params?.map { it.asInput })
                )
            )
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetFulfilmentPointsQuery.Data>() {
                override fun onResponse(response: Response<GetFulfilmentPointsQuery.Data>) {
                    response.data?.getFulfilmentPoints?.apply {
                        val fulfilmentPoints =
                            edges?.map { result -> result?.fragments?.fragmentFulfilmentPoint?.asModel }
                        val paginatedObject = PaginatedObject(fulfilmentPoints, nextPage)
                        paginatedObject.extractResponse(response.errors)
                        callback.invoke(null, paginatedObject)
                    } ?: run { callback.invoke(Exception(), null) }

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
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, fulfilmentPoint: FulfilmentPoint?) -> Unit
    ) {
        try {
            val response =
                apolloClient.query(
                    GetFulfilmentPointByIdQuery(
                        id,
                        Input.optional(params?.map { it.asInput })
                    )
                )
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetFulfilmentPointByIdQuery.Data>() {
                override fun onResponse(response: Response<GetFulfilmentPointByIdQuery.Data>) {
                    val fulfilmentPoint =
                        response.data?.getFulfilmentPoint?.fragments?.fragmentFulfilmentPoint?.asModel
                    fulfilmentPoint.invokeCallback(response, callback)
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
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
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
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
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