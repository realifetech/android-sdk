package com.realifetech.core_sdk.feature.fulfilmentpoint.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
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
import com.realifetech.type.FulfilmentPointFilter

class FulfilmentPointBackendDataSource(private val apolloClient: ApolloClient) :
    FulfilmentPointRepository.DataSource {

    override suspend fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: Input<FulfilmentPointFilter>?
    ): Result<PaginatedObject<FulfilmentPoint?>> {
        return try {
            val response = apolloClient.query(
                GetFulfilmentPointsQuery(
                    pageSize = pageSize,
                    page = page.toInput(),
                    filters = filters ?: FulfilmentPointFilter().toInput()
                )
            ).await()
            val fulfilmentPoints =
                response.data?.getFulfilmentPoints?.edges?.map { result -> result?.fragments?.fragmentFulfilmentPoint?.asModel }
            val nextPage = response.data?.getFulfilmentPoints?.nextPage
            val paginatedObject = PaginatedObject(fulfilmentPoints, nextPage)
            paginatedObject.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getFulfilmentPointById(id: String): Result<FulfilmentPoint> {
        return try {
            val response =
                apolloClient.query(GetFulfilmentPointByIdQuery(id)).await()
            response.data?.getFulfilmentPoint?.fragments?.fragmentFulfilmentPoint
                .extractResponse(response.errors) { it.asModel }
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int
    ): Result<PaginatedObject<FulfilmentPointCategory?>> {
        return try {
            val response = apolloClient.query(
                GetFulfilmentPointCategoriesQuery(
                    pageSize = pageSize,
                    page = page.toInput()
                )
            ).await()
            val fulfilmentPointCategories =
                response.data?.getFulfilmentPointCategories?.edges?.map { result -> result?.fragments?.fragmentFulfilmentPointCategory?.asModel }
            val nextPage = response.data?.getFulfilmentPointCategories?.nextPage
            val paginatedObject = PaginatedObject(fulfilmentPointCategories, nextPage)
            paginatedObject.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getFulfilmentPointCategoryById(
        id: String
    ): Result<FulfilmentPointCategory> {
        return try {
            val response = apolloClient.query(
                GetFulfilmentPointCategoryByIdQuery(id)
            ).await()
            response.data?.getFulfilmentPointCategory?.fragments?.fragmentFulfilmentPointCategory
                .extractResponse(response.errors) {
                    it.asModel
                }
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }
}