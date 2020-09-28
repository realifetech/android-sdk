package com.realifetech.sdk.analytics.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Contains information about an [AnalyticsEvent] which was not sent, and its saved in Database.
 */
@Entity(tableName = "PendingEvents")
internal data class PendingAnalyticsEvent(
    @ColumnInfo(name = "event") val event: AnalyticsEvent,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)