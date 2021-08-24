package com.realifetech.sdk.sell.basket

import com.realifetech.sdk.sell.basket.domain.BasketRepository
import com.realifetech.sdk.sell.basket.di.BasketModuleProvider

internal class BasketProvider {
    fun provideBasketRepository(): BasketRepository {
        return BasketModuleProvider.provideBasketModule()
    }
}