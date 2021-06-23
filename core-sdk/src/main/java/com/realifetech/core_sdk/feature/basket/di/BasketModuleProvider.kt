package com.realifetech.core_sdk.feature.basket.di

import com.realifetech.core_sdk.feature.basket.BasketRepository
import com.realifetech.core_sdk.feature.basket.data.BasketDataSource

object BasketModuleProvider {
    fun provideBasketModule(): BasketRepository {
        return BasketRepository(BasketDataSource())
    }
}