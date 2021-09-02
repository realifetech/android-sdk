package com.realifetech.sdk.di.features.modules

import android.content.Context
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.general.data.PhysicalDeviceInfo
import com.realifetech.sdk.general.domain.DeviceNetworkDataSource
import com.realifetech.sdk.general.domain.DeviceRepositoryNetworkDataSource
import dagger.Module
import dagger.Provides

@Module
object GeneralModule {

    @Provides
    fun deviceRepositoryNetworkDataSource(
        realifetechApiV3Service: RealifetechApiV3Service,
        context: Context
    ): DeviceNetworkDataSource {
        return DeviceRepositoryNetworkDataSource(
            realifetechApiV3Service,
            PhysicalDeviceInfo(context)
        )
    }

}
