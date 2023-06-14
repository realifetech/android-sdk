package com.realifetech.sdk.sell.fulfilmentpoint.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
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
import com.realifetech.type.FulfilmentPointFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FulfilmentPointDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    FulfilmentPointDataSource {
    override suspend fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: FulfilmentPointFilter?,
        params: List<FilterParamWrapper>?
    ): PaginatedObject<FulfilmentPoint?>? {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetFulfilmentPointsQuery(
                    pageSize,
                    Optional.presentIfNotNull(page),
                    Optional.presentIfNotNull(filters),
                    Optional.presentIfNotNull(params?.map { it.asInput })
                )
                val response = apolloClient.query(query).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.getFulfilmentPoints?.run {
                        val fulfilmentPoints =
                            edges?.mapNotNull { it?.fragmentFulfilmentPoint?.asModel }
                        PaginatedObject(fulfilmentPoints, nextPage)
                    }
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun getFulfilmentPointById(
        id: String,
        params: List<FilterParamWrapper>?
    ): FulfilmentPoint? {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetFulfilmentPointByIdQuery(
                    id,
                    Optional.presentIfNotNull(params?.map { it.asInput })
                )
                val response = apolloClient.query(query).execute()
                if (response.errors?.isNotEmpty() == true) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.getFulfilmentPoint?.fragmentFulfilmentPoint?.asModel
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }


    override suspend fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int
    ): PaginatedObject<FulfilmentPointCategory?> {
        return withContext(Dispatchers.IO) {
            try {
                val query =
                    GetFulfilmentPointCategoriesQuery(pageSize, Optional.presentIfNotNull(page))
                val response = apolloClient.query(query).execute()
                if (response.errors?.isNotEmpty() == true) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.getFulfilmentPointCategories?.let {
                        val fulfilmentPointCategories =
                            it.edges?.mapNotNull { it?.fragmentFulfilmentPointCategory?.asModel }
                        PaginatedObject(fulfilmentPointCategories, it.nextPage)
                    } ?: PaginatedObject(
                        emptyList(),
                        null
                    ) // Default value if getFulfilmentPointCategories is null
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun getFulfilmentPointCategoryById(id: String): FulfilmentPointCategory? {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetFulfilmentPointCategoryByIdQuery(id)
                val response = apolloClient.query(query).execute()
                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.getFulfilmentPointCategory?.fragmentFulfilmentPointCategory?.asModel
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }
}