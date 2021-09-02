package com.realifetech.sdk.analytics.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.realifetech.sdk.analytics.domain.AnalyticsEventDatabaseConverter
import com.realifetech.sdk.analytics.domain.PendingAnalyticsEvent
import javax.inject.Inject

@Database(entities = [PendingAnalyticsEvent::class], version = 1)
@TypeConverters(AnalyticsEventDatabaseConverter::class)
internal abstract class PendingAnalyticEventsDatabase:
    RoomDatabase() {
    abstract fun pendingEventsDao(): PendingAnalyticEventsDao

}