package com.realifetech.core_sdk.feature.fulfilmentpoint.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.GetFulfilmentPointByIdQuery
import com.realifetech.GetFulfilmentPointCategoriesQuery
import com.realifetech.GetFulfilmentPointCategoryByIdQuery
import com.realifetech.GetFulfilmentPointsQuery
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.fulfilmentpoint.FulfilmentPointRepository
import com.realifetech.core_sdk.feature.fulfilmentpoint.domain.FulfilmentPoint
import com.realifetech.core_sdk.feature.fulfilmentpoint.domain.FulfilmentPointCategory
import com.realifetech.core_sdk.feature.fulfilmentpoint.domain.FulfilmentPointCategoryEdge
import com.realifetech.core_sdk.feature.helper.extractResponse
import com.realifetech.fragment.FragmentFulfilmentPoint
import com.realifetech.fragment.FragmentFulfilmentPointCategory
import com.realifetech.type.FulfilmentPointFilter

class FulfilmentPointBackendDataSource(private val apolloClient: ApolloClient) :
    FulfilmentPointRepository.DataSource {

    override suspend fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: Input<FulfilmentPointFilter>?
    ): Result<List<FulfilmentPoint>> {
        return try {
            val response = apolloClient.query(
                GetFulfilmentPointsQuery(
                    pageSize = pageSize,
                    page = page.toInput(),
                    filters = filters ?: FulfilmentPointFilter().toInput()
                )
            ).await()
            response.data?.getFulfilmentPoints.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getFulfilmentPointById(id: String): Result<FulfilmentPoint> {
        return try {
            val response =
                apolloClient.query(GetFulfilmentPointByIdQuery(id)).await()
            response.data?.getFulfilmentPoint?.fragments?.fragmentFulfilmentPoint
                ?.asFulfilmentPoint.extractResponse(
                    response.errors
                )
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int
    ): Result<FulfilmentPointCategoryEdge> {
        return try {
            val response = apolloClient.query(
                GetFulfilmentPointCategoriesQuery(
                    pageSize = pageSize,
                    page = page.toInput()
                )
            ).await()
            response.data?.me?.fulfilmentPointCategories.extractResponse(response.errors)
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
            response.data?.me?.fulfilmentPointCategory?.fragments?.fragmentFulfilmentPointCategory
                ?.asFulfilmentPointCategory.extractResponse(
                    response.errors
                )
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    private fun GetFulfilmentPointsQuery.GetFulfilmentPoints?.extractResponse(errors: List<Error>?): Result<List<FulfilmentPoint>> {
        val fulfilmentPointList: List<FulfilmentPoint> =
            this?.edges?.mapNotNull { edge ->
                edge?.fragments?.fragmentFulfilmentPoint?.asFulfilmentPoint
            } ?: listOf()

        return fulfilmentPointList.extractResponse(errors)
    }

    private val FragmentFulfilmentPoint.asFulfilmentPoint: FulfilmentPoint
        get() =
            FulfilmentPoint(
                id = id,
                status = status,
                externalId = externalId,
                imageUrl = imageUrl,
                mapImageUrl = mapImageUrl,
                lat = lat,
                long = long_,
                type = type,
                position = position,
                translations = translations?.filterNotNull(),
                categories = categories?.filterNotNull(),
                timeslots = timeslots?.filterNotNull(),
                seatForm = seatForm,
                venue = venue,
                prepTime = prepTime,
                waitTime = waitTime,
                reference = reference
            )

    private fun GetFulfilmentPointCategoriesQuery.FulfilmentPointCategories
    ?.extractResponse(errors: List<Error>?): Result<FulfilmentPointCategoryEdge> {
        val categoryList: List<FulfilmentPointCategory> = this?.edges?.mapNotNull {
            it!!.fragments.fragmentFulfilmentPointCategory.asFulfilmentPointCategory
        } ?: listOf()

        val categoryEdge = FulfilmentPointCategoryEdge(
            nextPage = this?.nextPage,
            lastPage = this?.lastPage,
            firstPage = this?.firstPage,
            items = categoryList
        )

        return categoryEdge.extractResponse(errors)
    }

    private val FragmentFulfilmentPointCategory.asFulfilmentPointCategory: FulfilmentPointCategory
        get() = FulfilmentPointCategory(
            id = id,
            reference = reference,
            status = status,
            iconImageUrl = iconImageUrl,
            position = position,
            translations = translations
        )
}