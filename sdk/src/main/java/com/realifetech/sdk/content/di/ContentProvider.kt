package com.realifetech.sdk.content.di

import com.realifetech.sdk.content.webPage.di.WebPageModuleProvider
import com.realifetech.sdk.content.webPage.domain.WebPageRepository


internal class ContentProvider() {
    fun provideWebPageRepository(): WebPageRepository {
        return WebPageModuleProvider.provideWebPageRepository()
    }
}