package com.realifetech.sdk.di.core

import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
import com.realifetech.sdk.core.data.datasource.AuthApiDataSource
import com.realifetech.sdk.core.data.datasource.AuthApiDataSourceImpl
import com.realifetech.sdk.core.network.DeviceIdInterceptor
import com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor
import com.realifetech.sdk.core.network.OAuth2Authenticator
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module(includes = [GraphQlModule::class])
object RestClientV3Module {

    @CoreScope
    @Provides
    @Named("client-V3")
    internal fun httpClient(
        deviceIdInterceptor: DeviceIdInterceptor,
        platformPreferences: PlatformPreferences,
        authTokenStorage: AuthTokenStorage,
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
    fun retrofit(
        @Named("client-V3") httpClient: OkHttpClient,
        configurationStorage: ConfigurationStorage
    ): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(configurationStorage.apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @CoreScope
    @Provides
    fun realifetechApiV3Service(retrofit: Retrofit): RealifetechApiV3Service {
        return retrofit.create(RealifetechApiV3Service::class.java)
    }

    @CoreScope
    @Provides
    internal fun authenticationBackendApiDataSource(realifetechApiV3Service: RealifetechApiV3Service): AuthApiDataSource =
        AuthApiDataSourceImpl(realifetechApiV3Service)

}