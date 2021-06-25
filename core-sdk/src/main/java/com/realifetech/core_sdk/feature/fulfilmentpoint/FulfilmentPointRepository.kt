package com.realifetech.core_sdk.feature.fulfilmentpoint

import com.apollographql.apollo.api.Input
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.type.FulfilmentPointFilter

class FulfilmentPointRepository(private val dataSource: DataSource) {

    fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: Input<FulfilmentPointFilter>?,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPoint?>?) -> Unit
    ) {
        dataSource.getFulfilmentPoints(
            pageSize = pageSize,
            page = page,
            filters = filters,
            callback = callback
        )
    }

    fun getFulfilmentPointById(
        id: String,
        callback: (error: Exception?, fulfilmentPoint: FulfilmentPoint?) -> Unit
    ) {
        dataSource.getFulfilmentPointById(id, callback)
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
        fun getFulfilmentPoints(
            pageSize: Int,
            page: Int,
            filters: Input<FulfilmentPointFilter>?,
            callback: (error: Exception?, response: PaginatedObject<FulfilmentPoint?>?) -> Unit
        )

        fun getFulfilmentPointById(
            id: String,
            callback: (error: Exception?, fulfilmentPoint: FulfilmentPoint?) -> Unit
        )

        fun getFulfilmentPointCategories(
            pageSize: Int,
            page: Int,
            callback: (error: Exception?, response: PaginatedObject<FulfilmentPointCategory?>?) -> Unit
        )

        fun getFulfilmentPointCategoryById(
            id: String,
            callback: (error: Exception?, fulfilmentPointCategory: FulfilmentPointCategory?) -> Unit
        )
    }
}