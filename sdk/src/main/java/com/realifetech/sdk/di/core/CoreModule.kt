package com.realifetech.sdk.di.core

import android.content.Context
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.data.database.preferences.platform.PlatformPreferences
import com.realifetech.sdk.core.data.model.config.CoreConfiguration
import com.realifetech.sdk.core.utils.ColorPallet
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

}