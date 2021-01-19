package com.realifetech.core_sdk.network.graphQl

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.core_sdk.di.CoreProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object GraphQlModule {

     private const val APOLLO_DB: String ="apollo.db"
    fun getApolloClient(serverUrl: String, context: Context? = null): ApolloClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val apolloClient = ApolloClient.builder()
            .serverUrl(serverUrl)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(CoreProvider.graphQlInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
        context?.let {
            apolloClient.normalizedCache(SqlNormalizedCacheFactory(it,APOLLO_DB ))
                .defaultResponseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
        }


        return apolloClient.build()
    }
}