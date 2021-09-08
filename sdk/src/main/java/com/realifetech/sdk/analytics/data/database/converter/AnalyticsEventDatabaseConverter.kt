package com.realifetech.sdk.analytics.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper

internal class AnalyticsEventDatabaseConverter {
    @TypeConverter
    fun fromEvent(event: AnalyticEventWrapper): String {
        return Gson().toJson(event)
    }

    @TypeConverter
    fun fromJsonToEvent(json: String): AnalyticEventWrapper {
        return Gson().fromJson(json, AnalyticEventWrapper::class.java)
    }
}