package com.realifetech.sdk.analytics.di

import com.realifetech.sdk.analytics.data.AnalyticsDatabaseStorageDataSource
import com.realifetech.sdk.analytics.data.AnalyticsEngine
import com.realifetech.sdk.analytics.data.AnalyticsStorage
import com.realifetech.sdk.analytics.data.RtlBackendAnalyticsEngine
import com.realifetech.sdk.analytics.database.PendingAnalyticEventsDatabase
import com.realifetech.sdk.analytics.network.GraphQlModule

internal object AnalyticsProvider {
    fun provideAnalyticsStorage(): AnalyticsStorage {
        val dao = PendingAnalyticEventsDatabase.getInstance().pendingEventsDao()
        return AnalyticsStorage(AnalyticsDatabaseStorageDataSource(dao))
    }

    fun provideAnalyticsEngine(): AnalyticsEngine {
        return RtlBackendAnalyticsEngine(GraphQlModule.apolloClient)
    }
}