package com.realifetech.sdk.sell.basket

import com.realifetech.core_sdk.feature.basket.BasketRepository
import com.realifetech.core_sdk.feature.basket.di.BasketModuleProvider

internal class BasketProvider {
    fun provideBasketRepository(): BasketRepository {
        return BasketModuleProvider.provideBasketModule()
    }
}