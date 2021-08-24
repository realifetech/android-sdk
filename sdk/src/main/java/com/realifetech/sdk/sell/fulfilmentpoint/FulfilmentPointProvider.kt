package com.realifetech.sdk.sell.fulfilmentpoint

import com.realifetech.sdk.sell.fulfilmentpoint.domain.FulfilmentPointRepository
import com.realifetech.sdk.sell.fulfilmentpoint.di.FulfilmentPointModuleProvider

internal class FulfilmentPointProvider {
    fun provideFulfilmentPointRepository(): FulfilmentPointRepository {
        return FulfilmentPointModuleProvider.provideFulfilmentPointRepository()
    }
}