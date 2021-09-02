package com.realifetech.sdk.di.core

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.sdk.core.database.configuration.ConfigurationStorage
import com.realifetech.sdk.core.database.preferences.Preferences
import com.realifetech.sdk.core.network.DeviceIdInterceptor
import com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor
import com.realifetech.sdk.core.network.OAuth2Authenticator
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named

@Module
class GraphQlModule {

    @CoreScope
    @Provides
    @Named("client-graphQL")
    internal fun httpClient(
        deviceIdInterceptor: DeviceIdInterceptor,
        oAuthInterceptor: OAuth2AuthenticationInterceptor,
        oAuth2Authenticator: OAuth2Authenticator,
        preferences: Preferences
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(oAuthInterceptor)
            .authenticator(oAuth2Authenticator)
            .addInterceptor(deviceIdInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    }

    @CoreScope
    @Provides
    fun apolloClient(
        context: Context,
        @Named("client-graphQL") okHttpClient: OkHttpClient,
        configurationStorage: ConfigurationStorage,
    ): ApolloClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val apolloClient = ApolloClient.builder()
            .serverUrl(configurationStorage.graphQl)
            .okHttpClient(okHttpClient)
        apolloClient.normalizedCache(
            SqlNormalizedCacheFactory(
                context,
                APOLLO_DB
            )
        ).defaultResponseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)

        return apolloClient.build()
    }

    companion object {
        private const val APOLLO_DB: String = "apollo.db"

    }
}