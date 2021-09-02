package com.realifetech.sdk.core.network.graphQl

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.sdk.core.domain.RLTConfiguration
import com.realifetech.sdk.core.network.OAuth2Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

internal class GraphQlModule @Inject constructor(
    private val graphQlInterceptor: GraphQlHeadersInterceptor,
    private val oAuth2Authenticator: OAuth2Authenticator
) {

    fun createApolloClient(context: Context): ApolloClient {
        val serverUrl = RLTConfiguration.GRAPHQL_URL
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val apolloClient = ApolloClient.builder()
            .serverUrl(serverUrl)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(graphQlInterceptor)
                    .authenticator(oAuth2Authenticator)
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
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