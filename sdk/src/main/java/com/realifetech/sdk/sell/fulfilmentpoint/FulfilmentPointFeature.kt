package com.realifetech.sdk.sell.fulfilmentpoint

import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.model.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.model.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.fulfilmentpoint.domain.FulfilmentPointRepository
import com.realifetech.type.FulfilmentPointFilter
import javax.inject.Inject

class FulfilmentPointFeature @Inject constructor(private val fulfilmentPointRepo: FulfilmentPointRepository) {

    suspend fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: FulfilmentPointFilter?,
        params: List<FilterParamWrapper>?
    ): PaginatedObject<FulfilmentPoint?>? {
        return fulfilmentPointRepo.getFulfilmentPoints(pageSize, page, filters, params)
    }

    suspend fun getFulfilmentPointById(
        id: String,
        params: List<FilterParamWrapper>?
    ): FulfilmentPoint? {
        return fulfilmentPointRepo.getFulfilmentPointById(id, params)
    }

    suspend fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int
    ): PaginatedObject<FulfilmentPointCategory?> {
        return fulfilmentPointRepo.getFulfilmentPointCategories(pageSize, page)
    }

    suspend fun getFulfilmentPointCategoryById(id: String): FulfilmentPointCategory? {
        return fulfilmentPointRepo.getFulfilmentPointCategoryById(id)
    }
}