package com.realifetech.sdk.analytics.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.analytics.domain.AnalyticsEventDatabaseConverter
import com.realifetech.sdk.analytics.domain.PendingAnalyticsEvent

@Database(entities = [PendingAnalyticsEvent::class], version = 1)
@TypeConverters(AnalyticsEventDatabaseConverter::class)
internal abstract class PendingAnalyticEventsDatabase : RoomDatabase() {
    abstract fun pendingEventsDao(): PendingAnalyticEventsDao

    companion object {
        private const val DATABASE_NAME = "PendingEvents.db"

        private val databaseInstance: PendingAnalyticEventsDatabase by lazy {
            Room.databaseBuilder(
                RealifeTech.getGeneral().configuration.requireContext(),
                PendingAnalyticEventsDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

        @Synchronized
        fun getInstance(): PendingAnalyticEventsDatabase {
            return databaseInstance
        }
    }
}