package com.realifetech.sdk.analytics.domain

data class AnalyticsEvent(
    val type: String,
    val action: String,
    val new: Map<String, Any>?,
    val old: Map<String, Any>?
)