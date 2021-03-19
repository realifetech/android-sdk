package com.realifetech.core_sdk.feature.webPage.di

import com.realifetech.core_sdk.feature.webPage.WebPageRepository
import com.realifetech.core_sdk.feature.webPage.data.WebPageDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object WebPageModuleProvider {
    fun provideWebPageRepository(baseUrl: String): WebPageRepository {
        val client = GraphQlModule.getApolloClient(baseUrl)
        return WebPageRepository(WebPageDataSource(client))
    }
}