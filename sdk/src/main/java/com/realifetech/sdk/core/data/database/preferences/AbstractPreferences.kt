package com.realifetech.sdk.core.data.database.preferences

import android.content.Context
import android.content.SharedPreferences

open class AbstractPreferences(private val context: Context) {

    open val preferencesStorageName: String = SDK_PREFERENCES

   internal val preferences: SharedPreferences
        get() = context.getSharedPreferences(
            getPreferencesName(),
            Context.MODE_PRIVATE
        )


    private fun getPreferencesName(): String {
        return preferencesStorageName + context.packageName
    }

    companion object {
        private const val SDK_PREFERENCES = "RLT_SDK_Preferences"
        internal const val EMPTY = ""

    }

}