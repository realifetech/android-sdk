package com.realifetech.sdk.analytics.data.model

data class AnalyticsEvent(
    val type: String,
    val action: String,
    val new: Map<String, Any>?,
    val old: Map<String, Any>?,
    val creationTimeMillisecondsSince1970: Long
)