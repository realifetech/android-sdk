package com.realifetech.core_sdk.network.graphQl

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.core_sdk.di.CoreProvider
import com.realifetech.core_sdk.domain.CoreConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object GraphQlModule {

    private const val APOLLO_DB: String = "apollo.db"

    val apolloClient: ApolloClient by lazy {
        createApolloClient()
    }

    private fun createApolloClient(): ApolloClient {
        val serverUrl = CoreConfiguration.graphApiUrl
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val apolloClient = ApolloClient.builder()
            .serverUrl(serverUrl)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(CoreProvider.graphQlInterceptor)
                    .authenticator(CoreProvider.oAuth2Authenticator)
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
        apolloClient.normalizedCache(
            SqlNormalizedCacheFactory(
                CoreConfiguration.context,
                APOLLO_DB
            )
        ).defaultResponseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)


        return apolloClient.build()
    }
}