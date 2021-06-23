package com.realifetech.core_sdk.feature.webPage.di

import com.realifetech.core_sdk.feature.webPage.WebPageRepository
import com.realifetech.core_sdk.feature.webPage.data.WebPageDataSource

object WebPageModuleProvider {
    fun provideWebPageRepository(): WebPageRepository {
        return WebPageRepository(WebPageDataSource())
    }
}