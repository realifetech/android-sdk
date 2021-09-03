package com.realifetech.sdk.analytics.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.realifetech.sdk.analytics.data.model.AnalyticsEvent

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