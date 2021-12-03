package com.realifetech.sdk.di.core

import android.content.Context
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
import com.realifetech.sdk.core.data.datasource.AuthApiDataSource
import com.realifetech.sdk.core.data.model.config.CoreConfiguration
import com.realifetech.sdk.core.domain.OAuthManager
import com.realifetech.sdk.core.utils.ColorPallet
import com.realifetech.sdk.core.utils.DeviceCalendar
import com.realifetech.sdk.core.utils.DeviceCalendarWrapper
import dagger.Lazy
import dagger.Module
import dagger.Provides

@Module
class CoreModule(private val context: Context, private val configuration: CoreConfiguration) {

    @CoreScope
    @Provides
    internal fun context(): Context = context

    @CoreScope
    @Provides
    internal fun colorPallet(context: Context): ColorPallet = ColorPallet(context)

    @CoreScope
    @Provides
    internal fun deviceCalendar(): DeviceCalendar = DeviceCalendarWrapper()


    @CoreScope
    @Provides
    fun preference(context: Context) = PlatformPreferences(context)

    @CoreScope
    @Provides
    fun configurationStorage(context: Context): ConfigurationStorage {
        val configStorage = ConfigurationStorage(context)
        configStorage.set(configuration)
        return configStorage
    }


    @CoreScope
    @Provides
    fun authTokenStorage(context: Context) = AuthTokenStorage(context)

    @CoreScope
    @Provides
    fun oauthManager(
        authTokenStorage: AuthTokenStorage,
        authApiSource: Lazy<AuthApiDataSource>,
        platformTokenStorage: PlatformPreferences,
        configurationStorage: ConfigurationStorage
    ) = OAuthManager(
        authTokenStorage,
        authApiSource,
        platformTokenStorage,
        configurationStorage
    )


}