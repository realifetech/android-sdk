package com.realifetech.sdk.sell.fulfilmentpoint.domain

import com.apollographql.apollo.api.Input
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.fulfilmentpoint.data.FulfilmentPointDataSource
import com.realifetech.type.FulfilmentPointFilter
import javax.inject.Inject

class FulfilmentPointRepository @Inject constructor(private val dataSource: FulfilmentPointDataSource) {

    fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: Input<FulfilmentPointFilter>?,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPoint?>?) -> Unit
    ) {
        dataSource.getFulfilmentPoints(
            pageSize = pageSize,
            page = page,
            filters = filters,
            params = params,
            callback = callback
        )
    }

    fun getFulfilmentPointById(
        id: String,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, fulfilmentPoint: FulfilmentPoint?) -> Unit
    ) {
        dataSource.getFulfilmentPointById(id, params, callback)
    }

    fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPointCategory?>?) -> Unit
    ) {
        dataSource.getFulfilmentPointCategories(pageSize, page, callback)
    }

    fun getFulfilmentPointCategoryById(
        id: String,
        callback: (error: Exception?, fulfilmentPointCategory: FulfilmentPointCategory?) -> Unit
    ) {
        dataSource.getFulfilmentPointCategoryById(id, callback)
    }

    interface DataSource {

    }
}