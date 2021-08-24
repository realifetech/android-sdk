package com.realifetech.sdk.analytics.data

import com.realifetech.sdk.analytics.domain.AnalyticsEvent
import com.realifetech.sdk.core.domain.Result

internal interface AnalyticsEngine {
    suspend fun logEvent(event: AnalyticsEvent): Result<Boolean>
}