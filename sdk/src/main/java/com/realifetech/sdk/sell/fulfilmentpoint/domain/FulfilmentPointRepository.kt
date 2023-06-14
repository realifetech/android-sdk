package com.realifetech.sdk.sell.fulfilmentpoint.domain

import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.fulfilmentpoint.data.FulfilmentPointDataSource
import com.realifetech.type.FulfilmentPointFilter
import javax.inject.Inject

class FulfilmentPointRepository @Inject constructor(private val dataSource: FulfilmentPointDataSource) {

    suspend fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: FulfilmentPointFilter?,
        params: List<FilterParamWrapper>?
    ): PaginatedObject<FulfilmentPoint?>? {
        return dataSource.getFulfilmentPoints(pageSize, page, filters, params)
    }

    suspend fun getFulfilmentPointById(
        id: String,
        params: List<FilterParamWrapper>?
    ): FulfilmentPoint? {
        return dataSource.getFulfilmentPointById(id, params)
    }

    suspend fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int
    ): PaginatedObject<FulfilmentPointCategory?> {
        return dataSource.getFulfilmentPointCategories(pageSize, page)
    }

    suspend fun getFulfilmentPointCategoryById(id: String): FulfilmentPointCategory? {
        return dataSource.getFulfilmentPointCategoryById(id)
    }
}