package com.realifetech.sdk.analytics.data

import com.realifetech.sdk.analytics.domain.AnalyticsEvent
import com.realifetech.sdk.domain.Result

interface AnalyticsEngine {
    fun logEvent(event: AnalyticsEvent): Result<Unit>
}