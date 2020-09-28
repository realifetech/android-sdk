package com.realifetech.sdk.analytics.data

import com.realifetech.sdk.analytics.domain.AnalyticsEvent
import com.realifetech.sdk.analytics.domain.PendingAnalyticsEvent

/**
 * Manages the storage of the [AnalyticsEvent] in database and extraction of it in the form of [PendingAnalyticsEvent]
 */
internal class AnalyticsStorage(private val dataSource: DataSource) {

    fun save(event: AnalyticsEvent) = dataSource.save(event)

    fun getAll(): List<PendingAnalyticsEvent> = dataSource.getAll()

    fun remove(event: PendingAnalyticsEvent) {
        dataSource.remove(event)
    }

    interface DataSource {
        fun save(event: AnalyticsEvent)

        fun getAll(): List<PendingAnalyticsEvent>

        fun remove(event: PendingAnalyticsEvent)
    }
}