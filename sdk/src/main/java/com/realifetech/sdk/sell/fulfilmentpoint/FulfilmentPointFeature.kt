package com.realifetech.sdk.sell.fulfilmentpoint

import com.apollographql.apollo.api.toInput
import com.realifetech.sdk.core.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.sdk.core.data.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.sdk.core.data.shared.`object`.FilterParamWrapper
import com.realifetech.sdk.core.data.shared.`object`.PaginatedObject
import com.realifetech.type.FulfilmentPointFilter

class FulfilmentPointFeature private constructor() {
    private val fulfilmentPointRepo = FulfilmentPointProvider().provideFulfilmentPointRepository()

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

    private object Holder {
        val instance = FulfilmentPointFeature()
    }

    companion object {
        val INSTANCE: FulfilmentPointFeature by lazy { Holder.instance }
    }
}