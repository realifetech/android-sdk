package com.realifetech.sdk.di.core

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.sql.SqlNormalizedCacheFactory
import com.apollographql.apollo3.network.okHttpClient
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
import com.realifetech.sdk.core.domain.OAuthManager
import com.realifetech.sdk.core.network.*
import dagger.Lazy
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
        authTokenStorage: AuthTokenStorage,
        oAuthManager: Lazy<OAuthManager>,
        languageInterceptor: LanguageInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(OAuth2AuthenticationInterceptor(authTokenStorage, platformPreferences))
            .addInterceptor(deviceIdInterceptor)
            .addInterceptor(languageInterceptor)
            .authenticator(OAuth2Authenticator(oAuthManager))
            .addNetworkInterceptor(UnauthenticatedCaseParserInterceptor())
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
            .serverUrl(configurationStorage.graphApiUrl)
            .okHttpClient(okHttpClient)
        apolloClient.normalizedCache(SqlNormalizedCacheFactory(context, APOLLO_DB))
            .fetchPolicy(FetchPolicy.NetworkFirst)
        return apolloClient.build()
    }


    companion object {
        private const val APOLLO_DB: String = "apollo.db"

    }
}