package com.realifetech.sdk.sell.fulfilmentpoint

import com.realifetech.core_sdk.feature.fulfilmentpoint.FulfilmentPointRepository
import com.realifetech.core_sdk.feature.fulfilmentpoint.di.FulfilmentPointModuleProvider
import com.realifetech.sdk.general.General

internal class FulfilmentPointProvider {
    fun provideFulfilmentPointRepository(): FulfilmentPointRepository {
        return FulfilmentPointModuleProvider.provideFulfilmentPointRepository(General.instance.configuration.graphApiUrl)
    }
}