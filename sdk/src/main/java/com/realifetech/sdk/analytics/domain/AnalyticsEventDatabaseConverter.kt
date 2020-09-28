package com.realifetech.sdk.analytics.domain

import androidx.room.TypeConverter
import com.google.gson.Gson

internal class AnalyticsEventDatabaseConverter {
    @TypeConverter
    fun fromEvent(event: AnalyticsEvent): String {
        return Gson().toJson(event)
    }

    @TypeConverter
    fun fromJsonToEvent(json: String): AnalyticsEvent {
        return Gson().fromJson(json, AnalyticsEvent::class.java)
    }
}