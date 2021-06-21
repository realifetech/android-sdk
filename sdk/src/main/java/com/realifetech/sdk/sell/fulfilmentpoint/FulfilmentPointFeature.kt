package com.realifetech.sdk.sell.fulfilmentpoint

import com.apollographql.apollo.api.toInput
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPoint
import com.realifetech.core_sdk.data.fulfilmentPoint.FulfilmentPointCategory
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.sdk.general.utils.executeCallback
import com.realifetech.type.FulfilmentPointFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FulfilmentPointFeature private constructor() {
    private val fulfilmentPointRepo = FulfilmentPointProvider().provideFulfilmentPointRepository()

    fun getFulfilmentPoints(
        pageSize: Int,
        page: Int,
        filters: FulfilmentPointFilter?,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPoint?>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = fulfilmentPointRepo.getFulfilmentPoints(pageSize, page, filters.toInput())
            executeCallback(result, callback)
        }
    }

    fun getFulfilmentPointById(
        id: String,
        callback: (error: Exception?, fulfilmentPoint: FulfilmentPoint?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = fulfilmentPointRepo.getFulfilmentPointById(id)
            executeCallback(result, callback)
        }
    }

    fun getFulfilmentPointCategories(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<FulfilmentPointCategory?>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = fulfilmentPointRepo.getFulfilmentPointCategories(pageSize, page)
            executeCallback(result, callback)
        }
    }

    fun getFulfilmentPointCategoryById(
        id: String,
        callback: (error: Exception?, fulfilmentPointCategory: FulfilmentPointCategory?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = fulfilmentPointRepo.getFulfilmentPointCategoryById(id)
            executeCallback(result, callback)
        }
    }

    private object Holder {
        val instance = FulfilmentPointFeature()
    }

    companion object {
        val INSTANCE: FulfilmentPointFeature by lazy { Holder.instance }
    }
}