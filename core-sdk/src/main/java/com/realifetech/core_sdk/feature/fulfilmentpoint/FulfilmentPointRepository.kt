package com.realifetech.core_sdk.feature.fulfilmentpoint

import com.apollographql.apollo.api.Input
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.fulfilmentpoint.domain.FulfilmentPoint
import com.realifetech.core_sdk.feature.fulfilmentpoint.domain.FulfilmentPointCategory
import com.realifetech.core_sdk.feature.fulfilmentpoint.domain.FulfilmentPointCategoryEdge
import com.realifetech.type.FulfilmentPointFilter
import io.reactivex.Flowable
import kotlinx.coroutines.rx2.rxSingle


class FulfilmentPointRepository(private val dataSource: DataSource) {

    fun getFulfilmentPoints(
        pageSize: Int,
        page: Int = 1,
        filters: Input<FulfilmentPointFilter>
    ): Flowable<Result<List<FulfilmentPoint>>> {
        return rxSingle {
            dataSource.getFulfilmentPoints(
                pageSize = pageSize,
                page = page,
                filters = filters
            )
        }.toFlowable()
    }

    suspend fun getFulfilmentPointsById(id: String) = dataSource.getFulfilmentPointCategoryById(id)

    fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int
    ): Flowable<Result<FulfilmentPointCategoryEdge>> {
        return rxSingle {
            dataSource.getFulfilmentPointCategories(
                pageSize,
                page
            )
        }.toFlowable()
    }

    suspend fun getFulfilmentPointsCategoryById(id: String) =
        dataSource.getFulfilmentPointCategoryById(id)

    interface DataSource {
        suspend fun getFulfilmentPoints(
            pageSize: Int,
            page: Int = 1,
            filters: Input<FulfilmentPointFilter>
        ): Result<List<FulfilmentPoint>>

        suspend fun getFulfilmentPointById(
            id: String
        ): Result<FulfilmentPoint>

        suspend fun getFulfilmentPointCategories(
            pageSize: Int,
            page: Int
        ): Result<FulfilmentPointCategoryEdge>

        suspend fun getFulfilmentPointCategoryById(
            id: String
        ): Result<FulfilmentPointCategory>
    }
}