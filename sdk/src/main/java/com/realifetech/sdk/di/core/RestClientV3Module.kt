package com.realifetech.sdk.di.core

import com.realifetech.sdk.core.data.auth.AuthenticationBackendApiDataSource
import com.realifetech.sdk.core.database.configuration.ConfigurationStorage
import com.realifetech.sdk.core.domain.ApiDataSource
import com.realifetech.sdk.core.network.AuthorizationApiNetwork
import com.realifetech.sdk.core.network.DeviceIdInterceptor
import com.realifetech.sdk.core.network.OAuth2AuthenticationInterceptor
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
    internal fun realifetechApiV3Service(retrofit: Retrofit): RealifetechApiV3Service {
        return retrofit.create(RealifetechApiV3Service::class.java)
    }

    @CoreScope
    @Provides
    internal fun authenticationBackendApiDataSource(configurationStorage: ConfigurationStorage): ApiDataSource =
        AuthenticationBackendApiDataSource(AuthorizationApiNetwork(configurationStorage))

}