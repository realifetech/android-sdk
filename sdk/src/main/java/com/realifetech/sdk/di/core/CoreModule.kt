package com.realifetech.sdk.di.core

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.core.data.auth.AuthenticationBackendApiDataSource
import com.realifetech.sdk.core.data.auth.AuthenticationTokenPreferenceStorage
import com.realifetech.sdk.core.data.auth.AuthenticationTokenStorage
import com.realifetech.sdk.core.database.preferences.Preferences
import com.realifetech.sdk.core.domain.AuthenticationToken
import com.realifetech.sdk.core.domain.RLTConfiguration
import com.realifetech.sdk.core.network.DeviceIdInterceptor
import com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor
import com.realifetech.sdk.core.network.OAuth2Authenticator
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.network.graphQl.GraphQlHeadersInterceptor
import com.realifetech.sdk.core.network.graphQl.GraphQlModule
import com.realifetech.sdk.core.utils.ColorPallet
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class CoreModule(private val context: Context) {

    @CoreScope
    @Provides
    internal fun provideContext(): Context {
        return context
    }

    @CoreScope
    @Provides
    internal fun colorPallet(context: Context): ColorPallet {
        return ColorPallet(context)
    }

    @CoreScope
    @Provides
    fun preference(context: Context) = Preferences(context)

    @CoreScope
    @Provides
    fun oAuth2Authenticator(authenticationToken: AuthenticationToken) =
        OAuth2Authenticator(authenticationToken)

    @CoreScope
    @Provides
    fun oAuthInterceptor(authenticationToken: AuthenticationToken) =
        OAuth2AuthenticationInterceptor(authenticationToken)

    @CoreScope
    @Provides
    fun deviceIdInterceptor(preferences: Preferences) = DeviceIdInterceptor(preferences)

    @CoreScope
    @Provides
    fun authenticationTokenStorage(context: Context): AuthenticationTokenStorage =
        AuthenticationTokenStorage(AuthenticationTokenPreferenceStorage(context))

    @CoreScope
    @Provides
    fun authenticationToken(
        authenticationTokenStorage: AuthenticationTokenStorage,
        preferences: Preferences
    ) =
        AuthenticationToken(
            authenticationTokenStorage,
            AuthenticationBackendApiDataSource(),
            preferences
        )

    @CoreScope
    @Provides
    fun graphQlInterceptor(authenticationToken: AuthenticationToken) =
        GraphQlHeadersInterceptor(authenticationToken)

    @CoreScope
    @Provides
    fun apolloClient(
        graphQlInterceptor: GraphQlHeadersInterceptor,
        oAuth2Authenticator: OAuth2Authenticator,
        context: Context
    ): ApolloClient {
        return GraphQlModule(graphQlInterceptor, oAuth2Authenticator).createApolloClient(context)
    }

    @CoreScope
    @Provides
    internal fun httpClient(
        deviceIdInterceptor: DeviceIdInterceptor,
        oAuthInterceptor: OAuth2AuthenticationInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(oAuthInterceptor)
            .addInterceptor(deviceIdInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

    }

    @CoreScope
    @Provides
    fun retrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(RLTConfiguration.finalApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @CoreScope
    @Provides
    internal fun realifetechApiV3Service(retrofit: Retrofit): RealifetechApiV3Service {
        return retrofit.create(RealifetechApiV3Service::class.java)
    }
}