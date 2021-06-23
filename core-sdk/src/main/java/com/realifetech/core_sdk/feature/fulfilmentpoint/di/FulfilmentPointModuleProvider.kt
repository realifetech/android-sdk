package com.realifetech.core_sdk.feature.fulfilmentpoint.di

import com.realifetech.core_sdk.feature.fulfilmentpoint.FulfilmentPointRepository
import com.realifetech.core_sdk.feature.fulfilmentpoint.data.FulfilmentPointBackendDataSource

object FulfilmentPointModuleProvider {
    fun provideFulfilmentPointRepository(): FulfilmentPointRepository {
        return FulfilmentPointRepository(FulfilmentPointBackendDataSource())
    }
}