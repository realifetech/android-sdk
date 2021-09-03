package com.realifetech.sdk.sell.fulfilmentpoint.data

import com.apollographql.apollo.api.Input
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.FulfilmentPointFilter

interface FulfilmentPointDataSource {
    fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: Input<FulfilmentPointFilter>?,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPoint?>?) -> Unit
    )

    fun getFulfilmentPointById(
        id: String,
        params: List<FilterParamWrapper>?,
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