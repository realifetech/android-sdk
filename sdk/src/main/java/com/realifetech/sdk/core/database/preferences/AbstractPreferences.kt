package com.realifetech.sdk.core.database.preferences

import android.content.Context
import android.content.SharedPreferences

abstract class AbstractPreferences(private val context: Context) {


    internal val preferences: SharedPreferences =
        context.getSharedPreferences(getPreferencesName(), Context.MODE_PRIVATE)


    internal fun getPreferencesName(): String {
        return REALIFETECH_PREFERENCES + context.packageName
    }

    companion object {
        private const val REALIFETECH_PREFERENCES = "LiveStyledPreferences"
        internal const val EMPTY = ""

    }

}