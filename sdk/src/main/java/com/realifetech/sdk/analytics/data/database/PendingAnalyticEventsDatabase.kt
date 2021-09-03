package com.realifetech.sdk.analytics.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.realifetech.sdk.analytics.data.database.converter.AnalyticsEventDatabaseConverter
import com.realifetech.sdk.analytics.data.database.Entity.PendingAnalyticsEvent

@Database(entities = [PendingAnalyticsEvent::class], version = 1)
@TypeConverters(AnalyticsEventDatabaseConverter::class)
internal abstract class PendingAnalyticEventsDatabase :
    RoomDatabase() {
    abstract fun pendingEventsDao(): PendingAnalyticEventsDao

}