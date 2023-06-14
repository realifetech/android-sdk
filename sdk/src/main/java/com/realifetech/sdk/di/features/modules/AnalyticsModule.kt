package com.realifetech.sdk.di.features.modules

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo3.ApolloClient
import com.realifetech.sdk.analytics.data.database.PendingAnalyticEventsDatabase
import com.realifetech.sdk.analytics.data.datasource.AnalyticsStorageDataSourceImpl
import com.realifetech.sdk.analytics.domain.AnalyticsEngine
import com.realifetech.sdk.analytics.domain.AnalyticsStorage
import com.realifetech.sdk.analytics.domain.RltBackendAnalyticsEngine
import dagger.Module
import dagger.Provides

@Module
class AnalyticsModule {

    @Provides
    internal fun databaseInstance(context: Context): PendingAnalyticEventsDatabase {
        return Room.databaseBuilder(
            context,
            PendingAnalyticEventsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }


    @Provides
    internal fun provideAnalyticsStorage(pendingAnalyticEventsDatabase: PendingAnalyticEventsDatabase): AnalyticsStorage {
        val dao = pendingAnalyticEventsDatabase.pendingEventsDao()
        return AnalyticsStorage(AnalyticsStorageDataSourceImpl(dao))
    }

    @Provides
    fun provideAnalyticsEngine(apolloClient: ApolloClient, analyticsStorage: AnalyticsStorage): AnalyticsEngine {
        return RltBackendAnalyticsEngine(apolloClient,analyticsStorage)
    }


    companion object {
        private const val DATABASE_NAME = "PendingEvents.db"
    }
}
