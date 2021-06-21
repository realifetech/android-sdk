package com.realifetech.sdk.sell.basket

import com.realifetech.core_sdk.feature.basket.BasketRepository
import com.realifetech.core_sdk.feature.basket.di.BasketModuleProvider
import com.realifetech.sdk.general.General

internal class BasketProvider {
    fun provideBasketRepository(): BasketRepository {
        return BasketModuleProvider.provideBasketModule(General.instance.configuration.graphApiUrl)
    }
}