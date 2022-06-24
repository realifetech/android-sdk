package com.realifetech.sdk.analytics.data.model

data class AnalyticEventWrapper(
    val type: String,
    val action: String,
    val userId: String?,
    val new: Map<String, Any>?,
    val old: Map<String, Any>?,
    val creationTimeMillisecondsSince1970: Long
)