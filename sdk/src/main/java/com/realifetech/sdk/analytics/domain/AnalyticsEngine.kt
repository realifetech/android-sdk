package com.realifetech.sdk.analytics.domain

import com.realifetech.sdk.analytics.data.model.AnalyticsEvent
import com.realifetech.sdk.core.utils.Result

interface AnalyticsEngine {
    suspend fun logEvent(event: AnalyticsEvent): Result<Boolean>
}