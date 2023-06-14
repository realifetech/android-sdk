package com.realifetech.sdk.analytics.data.model

import com.apollographql.apollo3.api.Optional
import com.google.gson.Gson
import com.realifetech.type.AnalyticEvent
import java.text.SimpleDateFormat
import java.util.*

fun AnalyticEventWrapper.asAnalyticEvent() = AnalyticEvent(
    this.type,
    this.action,
    Optional.presentIfNotNull(this.new?.let { Gson().toJson(it) }),
    Optional.presentIfNotNull(this.old?.let { Gson().toJson(it) }),
    Optional.presentIfNotNull(EVENT_VERSION),
    SimpleDateFormat(
        DATE_FORMAT,
        Locale.getDefault()
    ).format(Date(this.creationTimeMillisecondsSince1970)),
    Optional.presentIfNotNull(this.userId)
)

private const val EVENT_VERSION = "1.0"
private const val DATE_FORMAT = "yyyy-MM-d'T'HH:mm:ssXXX"