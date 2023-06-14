package com.realifetech.sdk.di.features.modules

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.general.data.DeviceNetworkDataSource
import com.realifetech.sdk.general.data.DeviceNetworkDataSourceImpl
import com.realifetech.sdk.general.data.PhysicalDeviceInfo
import dagger.Module
import dagger.Provides

@Module
object GeneralModule {

    @Provides
    fun deviceRepositoryNetworkDataSource(
        apolloClient: ApolloClient,
        context: Context,
        configurationStorage: ConfigurationStorage
    ): DeviceNetworkDataSource = DeviceNetworkDataSourceImpl(
        apolloClient,
        PhysicalDeviceInfo(context, configurationStorage),
        configurationStorage
    )

}
