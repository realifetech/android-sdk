package com.realifetech.sdk.sell.fulfilmentpoint

import com.realifetech.core_sdk.feature.fulfilmentpoint.FulfilmentPointRepository
import com.realifetech.core_sdk.feature.fulfilmentpoint.di.FulfilmentPointModuleProvider

internal class FulfilmentPointProvider {
    fun provideFulfilmentPointRepository(): FulfilmentPointRepository {
        return FulfilmentPointModuleProvider.provideFulfilmentPointRepository()
    }
}