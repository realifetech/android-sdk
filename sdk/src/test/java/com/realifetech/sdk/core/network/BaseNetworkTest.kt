package com.realifetech.sdk.core.network

import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
import com.realifetech.sdk.core.domain.OAuthManager
import dagger.Lazy
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseNetworkTest {

    lateinit var server: MockWebServer

    @RelaxedMockK
    lateinit var platformPreferences: PlatformPreferences

    @RelaxedMockK
    lateinit var authTokenStorage: AuthTokenStorage

    @RelaxedMockK
    lateinit var oAuthManager: Lazy<OAuthManager>

    @RelaxedMockK
    lateinit var configurationStorage: ConfigurationStorage

    lateinit var realifetechApiV3Service: RealifetechApiV3Service

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        server = MockWebServer()
    }

    fun provideResponses(listOfResponses: List<MockResponse>) {
        val authenticator = OAuth2Authenticator(oAuthManager)
        val interceptor = OAuth2AuthenticationInterceptor(authTokenStorage, platformPreferences)
        val deviceInterceptor = DeviceIdInterceptor(configurationStorage)
        realifetechApiV3Service = provideService(
            server, interceptor, authenticator, deviceInterceptor, listOfResponses
        )
    }

    private fun provideService(
        server: MockWebServer,
        interceptor: OAuth2AuthenticationInterceptor,
        authenticator: OAuth2Authenticator,
        deviceInterceptor: DeviceIdInterceptor,
        mockResponses: List<MockResponse>
    ): RealifetechApiV3Service {
        for (mockResponse in mockResponses) {
            server.enqueue(mockResponse)
        }
        server.start()
        val baseUrl = server.url("/v3/")
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .authenticator(authenticator)
            .addInterceptor(deviceInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RealifetechApiV3Service::class.java)
    }
}