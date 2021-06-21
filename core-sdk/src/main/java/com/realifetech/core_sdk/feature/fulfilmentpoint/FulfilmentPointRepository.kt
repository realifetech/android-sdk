package com.realifetech.core_sdk.feature.fulfilmentpoint

import com.apollographql.apollo.api.Input
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.domain.Result
import com.realifetech.type.FulfilmentPointFilter

class FulfilmentPointRepository(private val dataSource: DataSource) {

    suspend fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: Input<FulfilmentPointFilter>?
    ) = dataSource.getFulfilmentPoints(
        pageSize = pageSize,
        page = page,
        filters = filters
    )

    suspend fun getFulfilmentPointById(id: String) = dataSource.getFulfilmentPointById(id)

    suspend fun getFulfilmentPointCategories(pageSize: Int, page: Int) =
        dataSource.getFulfilmentPointCategories(pageSize, page)

    suspend fun getFulfilmentPointCategoryById(id: String) =
        dataSource.getFulfilmentPointCategoryById(id)

    interface DataSource {
        suspend fun getFulfilmentPoints(
            pageSize: Int,
            page: Int,
            filters: Input<FulfilmentPointFilter>?
        ): Result<PaginatedObject<FulfilmentPoint?>>

        suspend fun getFulfilmentPointById(
            id: String
        ): Result<FulfilmentPoint>

        suspend fun getFulfilmentPointCategories(
            pageSize: Int,
            page: Int
        ): Result<PaginatedObject<FulfilmentPointCategory?>>

        suspend fun getFulfilmentPointCategoryById(
            id: String
        ): Result<FulfilmentPointCategory>
    }
}