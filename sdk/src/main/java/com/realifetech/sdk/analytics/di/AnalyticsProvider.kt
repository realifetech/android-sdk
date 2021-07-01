package com.realifetech.sdk.analytics.di

import com.realifetech.core_sdk.network.graphQl.GraphQlModule
import com.realifetech.sdk.analytics.data.AnalyticsDatabaseStorageDataSource
import com.realifetech.sdk.analytics.data.AnalyticsEngine
import com.realifetech.sdk.analytics.data.AnalyticsStorage
import com.realifetech.sdk.analytics.data.RltBackendAnalyticsEngine
import com.realifetech.sdk.analytics.database.PendingAnalyticEventsDatabase

internal object AnalyticsProvider {
    fun provideAnalyticsStorage(): AnalyticsStorage {
        val dao = PendingAnalyticEventsDatabase.getInstance().pendingEventsDao()
        return AnalyticsStorage(AnalyticsDatabaseStorageDataSource(dao))
    }

    fun provideAnalyticsEngine(): AnalyticsEngine {
        return RltBackendAnalyticsEngine(GraphQlModule.apolloClient)
    }
}