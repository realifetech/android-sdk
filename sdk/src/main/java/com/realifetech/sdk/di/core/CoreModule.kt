package com.realifetech.sdk.di.core

import android.content.Context
import com.realifetech.sdk.core.data.auth.AuthenticationTokenPreferenceStorage
import com.realifetech.sdk.core.data.auth.AuthenticationTokenStorage
import com.realifetech.sdk.core.database.configuration.ConfigurationStorage
import com.realifetech.sdk.core.database.preferences.Preferences
import com.realifetech.sdk.core.utils.ColorPallet
import dagger.Module
import dagger.Provides

@Module
class CoreModule(private val context: Context) {

    @CoreScope
    @Provides
    internal fun context(): Context {
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
    fun configurationStorage(context: Context) = ConfigurationStorage(context)

    @CoreScope
    @Provides
    fun authenticationTokenStorage(context: Context): AuthenticationTokenStorage =
        AuthenticationTokenStorage(AuthenticationTokenPreferenceStorage(context))

}