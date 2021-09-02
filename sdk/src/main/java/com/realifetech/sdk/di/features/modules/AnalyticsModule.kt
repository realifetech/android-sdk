package com.realifetech.sdk.di.features.modules

import android.content.Context
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.analytics.data.AnalyticsDatabaseStorageDataSource
import com.realifetech.sdk.analytics.data.AnalyticsEngine
import com.realifetech.sdk.analytics.data.AnalyticsStorage
import com.realifetech.sdk.analytics.data.RltBackendAnalyticsEngine
import com.realifetech.sdk.analytics.database.PendingAnalyticEventsDatabase
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
        return AnalyticsStorage(AnalyticsDatabaseStorageDataSource(dao))
    }

    @Provides
    fun provideAnalyticsEngine(apolloClient: ApolloClient): AnalyticsEngine {
        return RltBackendAnalyticsEngine(apolloClient)
    }


    companion object {
        private const val DATABASE_NAME = "PendingEvents.db"
    }
}
