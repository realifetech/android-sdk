package com.realifetech.sdk.content.webPage.di

import com.realifetech.sdk.content.webPage.domain.WebPageRepository
import com.realifetech.sdk.content.webPage.data.WebPageDataSource

object WebPageModuleProvider {
    fun provideWebPageRepository(): WebPageRepository {
        return WebPageRepository(WebPageDataSource())
    }
}