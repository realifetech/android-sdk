package com.realifetech.sdk.core.database.shared

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

interface SharedPreferencesManager<Entity : Any, Model : Any>

inline fun <reified Entity : Any, reified Model : Any> SharedPreferencesManager<Entity, Model>.saveListToSharedPreferences(
    list: List<Entity>,
    key: String,
    context: Context
) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = prefs.edit()

    val json: String = Gson().toJson(list)
    editor.putString(key, json)
    editor.apply()
}

inline fun <reified Entity : Any, reified Model : Any> SharedPreferencesManager<Entity, Model>.getListFromSharedPreferences(
    key: String,
    context: Context
): List<Entity>? {

    val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    val type: Type = TypeToken.getParameterized(List::class.java, Entity::class.java).type
    val json = prefs?.getString(key, null)
    return json?.let {
        Gson().fromJson(it, type) as List<Entity>
    }
}

inline fun <reified Entity : Any, reified Model : Any> SharedPreferencesManager<Entity, Model>.removeListFromSharedPreferences(
    key: String,
    context: Context
) {
    val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = prefs.edit()
    editor.remove(key)
    editor.apply()
}