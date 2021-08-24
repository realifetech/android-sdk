package com.realifetech.sdk.sell.fulfilmentpoint.di

import com.realifetech.sdk.sell.fulfilmentpoint.domain.FulfilmentPointRepository
import com.realifetech.sdk.sell.fulfilmentpoint.data.FulfilmentPointBackendDataSource

object FulfilmentPointModuleProvider {
    fun provideFulfilmentPointRepository(): FulfilmentPointRepository {
        return FulfilmentPointRepository(FulfilmentPointBackendDataSource())
    }
}