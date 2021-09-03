package com.realifetech.sdk.di.features.modules

import android.content.Context
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.general.data.PhysicalDeviceInfo
import com.realifetech.sdk.general.data.DeviceNetworkDataSource
import com.realifetech.sdk.general.data.DeviceNetworkDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
object GeneralModule {

    @Provides
    fun deviceRepositoryNetworkDataSource(
        realifetechApiV3Service: RealifetechApiV3Service,
        context: Context,
        configuration: ConfigurationStorage
    ): DeviceNetworkDataSource = DeviceNetworkDataSourceImpl(
        realifetechApiV3Service,
        PhysicalDeviceInfo(context),
        configuration
    )

}
