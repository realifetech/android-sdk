package com.realifetech.sdk.analytics.data.datasource

import com.realifetech.sdk.analytics.data.database.PendingAnalyticEventsDao
import com.realifetech.sdk.analytics.data.model.AnalyticsEvent
import com.realifetech.sdk.analytics.data.database.Entity.PendingAnalyticsEvent

internal class AnalyticsStorageDataSourceImpl(private val eventsDao: PendingAnalyticEventsDao) :
    AnalyticsStorageDataSource {

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