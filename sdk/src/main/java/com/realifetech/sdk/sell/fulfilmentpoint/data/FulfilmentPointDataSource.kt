package com.realifetech.sdk.sell.fulfilmentpoint.data

import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.FulfilmentPointFilter

interface FulfilmentPointDataSource {

    suspend fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: FulfilmentPointFilter?,
        params: List<FilterParamWrapper>?
    ): PaginatedObject<FulfilmentPoint?>?

    suspend fun getFulfilmentPointById(
        id: String,
        params: List<FilterParamWrapper>?
    ): FulfilmentPoint?

    suspend fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int
    ): PaginatedObject<FulfilmentPointCategory?>

    suspend fun getFulfilmentPointCategoryById(id: String): FulfilmentPointCategory?
}