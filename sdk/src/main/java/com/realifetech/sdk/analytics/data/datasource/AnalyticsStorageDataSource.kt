package com.realifetech.sdk.analytics.data.datasource

import com.realifetech.sdk.analytics.data.model.AnalyticsEvent
import com.realifetech.sdk.analytics.data.database.Entity.PendingAnalyticsEvent

interface AnalyticsStorageDataSource  {
    fun save(event: AnalyticsEvent)

    fun getAll(): List<PendingAnalyticsEvent>

    fun remove(event: PendingAnalyticsEvent)
}