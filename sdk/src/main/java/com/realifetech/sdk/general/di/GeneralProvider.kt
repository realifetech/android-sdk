package com.realifetech.sdk.general.di

import android.content.Context
import com.realifetech.sdk.general.General
import com.realifetech.sdk.general.data.PhysicalDeviceInfo
import com.realifetech.sdk.general.data.repositories.DeviceRepository
import com.realifetech.sdk.general.data.repositories.DeviceRepositoryNetworkDataSource
import com.realifetech.sdk.general.network.DeviceApiNetwork

internal class GeneralProvider(private val context: Context) {
    fun provideDeviceRegistration(): DeviceRepository {
        return DeviceRepository(DeviceRepositoryNetworkDataSource(DeviceApiNetwork.get(), PhysicalDeviceInfo(context)), General.instance.deviceRegistrationRetryPolicy)
    }
}