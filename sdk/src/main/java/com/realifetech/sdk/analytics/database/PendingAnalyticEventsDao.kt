package com.realifetech.sdk.analytics.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.realifetech.sdk.analytics.domain.PendingAnalyticsEvent

@Dao
internal interface PendingAnalyticEventsDao {
    @Insert
    fun save(event: PendingAnalyticsEvent)

    @Query("SELECT * FROM PendingEvents")
    fun getAll(): List<PendingAnalyticsEvent>

    @Delete
    fun remove(event: PendingAnalyticsEvent)
}