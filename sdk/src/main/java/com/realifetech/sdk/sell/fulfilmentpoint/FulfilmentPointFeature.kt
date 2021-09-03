package com.realifetech.sdk.sell.fulfilmentpoint

import com.apollographql.apollo.api.toInput
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.fulfilmentpoint.domain.FulfilmentPointRepository
import com.realifetech.type.FulfilmentPointFilter
import javax.inject.Inject

class FulfilmentPointFeature @Inject constructor(private val fulfilmentPointRepo: FulfilmentPointRepository) {

    fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: FulfilmentPointFilter?,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPoint?>?) -> Unit
    ) {
        fulfilmentPointRepo.getFulfilmentPoints(pageSize, page, filters.toInput(), params, callback)
    }

    fun getFulfilmentPointById(
        id: String,
        params: List<FilterParamWrapper>?,
        callback: (error: Exception?, fulfilmentPoint: FulfilmentPoint?) -> Unit
    ) {
        fulfilmentPointRepo.getFulfilmentPointById(id, params, callback)
    }

    fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPointCategory?>?) -> Unit
    ) {
        fulfilmentPointRepo.getFulfilmentPointCategories(pageSize, page, callback)

    }

    fun getFulfilmentPointCategoryById(
        id: String,
        callback: (error: Exception?, fulfilmentPointCategory: FulfilmentPointCategory?) -> Unit
    ) {
        fulfilmentPointRepo.getFulfilmentPointCategoryById(id, callback)
    }
}