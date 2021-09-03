package com.realifetech.sdk.di.core

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
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
        platformPreferences: PlatformPreferences,
        authTokenStorage: AuthTokenStorage
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(OAuth2AuthenticationInterceptor(authTokenStorage, platformPreferences))
            .authenticator(OAuth2Authenticator())
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