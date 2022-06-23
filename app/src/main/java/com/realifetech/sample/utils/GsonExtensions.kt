package com.realifetech.sample.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val gson = Gson()

inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}

fun <T> T.serializeToMap(): Map<String, Any> {
    return convert()
}