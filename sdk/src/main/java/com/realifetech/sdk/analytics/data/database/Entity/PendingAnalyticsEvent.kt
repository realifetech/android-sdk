package com.realifetech.sdk.analytics.data.database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.realifetech.sdk.analytics.data.model.AnalyticsEvent

/**
 * Contains information about an [AnalyticsEvent] which was not sent, and its saved in Database.
 */
@Entity(tableName = "PendingEvents")
data class PendingAnalyticsEvent(
    @ColumnInfo(name = "event") val event: AnalyticsEvent,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)