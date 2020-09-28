package com.realifetech.sdk.analytics.data

import com.realifetech.sdk.analytics.data.AnalyticsStorage
import com.realifetech.sdk.analytics.database.PendingAnalyticEventsDao
import com.realifetech.sdk.analytics.domain.AnalyticsEvent
import com.realifetech.sdk.analytics.domain.PendingAnalyticsEvent

internal class AnalyticsDatabaseStorageDataSource(private val eventsDao: PendingAnalyticEventsDao) :
    AnalyticsStorage.DataSource {

    override fun save(event: AnalyticsEvent) {
        eventsDao.save(PendingAnalyticsEvent(event))
    }

    override fun getAll(): List<PendingAnalyticsEvent> {
        return eventsDao.getAll()
    }

    override fun remove(event: PendingAnalyticsEvent) {
        eventsDao.remove(event)
    }
}