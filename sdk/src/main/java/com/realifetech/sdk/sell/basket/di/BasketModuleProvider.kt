package com.realifetech.sdk.sell.basket.di

import com.realifetech.sdk.sell.basket.domain.BasketRepository
import com.realifetech.sdk.sell.basket.data.BasketDataSource

object BasketModuleProvider {
    fun provideBasketModule(): BasketRepository {
        return BasketRepository(BasketDataSource())
    }
}