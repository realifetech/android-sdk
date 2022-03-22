package com.realifetech.sdk.analytics.data.model

import com.apollographql.apollo.api.Input
import com.google.gson.Gson
import com.realifetech.type.AnalyticEvent
import java.text.SimpleDateFormat
import java.util.*

fun AnalyticEventWrapper.asAnalyticEvent() = AnalyticEvent(
    this.type,
    this.action,
    Input.optional(this.new?.let { Gson().toJson(it) }),
    Input.optional(this.old?.let { Gson().toJson(it) }),
    Input.optional(EVENT_VERSION),
    SimpleDateFormat(
        DATE_FORMAT,
        Locale.getDefault()
    ).format(Date(this.creationTimeMillisecondsSince1970)),
    Input.optional(this.userId)
)

private const val EVENT_VERSION = "1.0"
private const val DATE_FORMAT = "yyyy-MM-d'T'HH:mm:ssXXX"