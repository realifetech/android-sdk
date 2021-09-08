package com.realifetech.sdk.analytics.data.database.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper

/**
 * Contains information about an [AnalyticEventWrapper] which was not sent, and its saved in Database.
 */
@Entity(tableName = "PendingEvents")
data class PendingAnalyticsEvent(
    @ColumnInfo(name = "event") val event: AnalyticEventWrapper,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)