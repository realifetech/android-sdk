package com.realifetech.sdk.analytics.domain

import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper

interface AnalyticsEngine {
    suspend fun logEvent(
        event: AnalyticEventWrapper,
        callback: (error: Exception?, response: Boolean) -> Unit
    )
}