package com.realifetech.core_sdk.feature.basket.di

import com.realifetech.core_sdk.feature.basket.BasketRepository
import com.realifetech.core_sdk.feature.basket.data.BasketDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object BasketModuleProvider {
    fun provideBasketModule(baseUrl: String): BasketRepository {
        val client = GraphQlModule.getApolloClient(baseUrl)
        return BasketRepository(BasketDataSource(client))
    }
}