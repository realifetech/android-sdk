package com.realifetech.sdk.analytics.domain

import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper

interface AnalyticsEngine {
    suspend fun track(event: AnalyticEventWrapper): Boolean

    suspend fun sendPendingEvents(): Boolean
}