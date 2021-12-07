package com.realifetech.sdk.analytics.domain

import com.realifetech.sdk.analytics.data.database.Entity.PendingAnalyticsEvent
import com.realifetech.sdk.analytics.data.datasource.AnalyticsStorageDataSource
import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper

/**
 * Manages the storage of the [AnalyticEventWrapper] in database and extraction of it in the form of [PendingAnalyticsEvent]
 */
class AnalyticsStorage(private val dataSource: AnalyticsStorageDataSource) {

    fun save(event: AnalyticEventWrapper) = dataSource.save(event)

    fun getAll(): List<PendingAnalyticsEvent> = dataSource.getAll()

    fun remove(event: PendingAnalyticsEvent) {
        dataSource.remove(event)
    }
}