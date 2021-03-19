package com.realifetech.sdk.content.di

import com.realifetech.core_sdk.feature.webPage.WebPageRepository
import com.realifetech.core_sdk.feature.webPage.di.WebPageModuleProvider
import com.realifetech.sdk.general.General

internal class ContentProvider() {
    fun provideWebPageRepository(): WebPageRepository {
        return WebPageModuleProvider.provideWebPageRepository(General.instance.configuration.graphApiUrl)
    }
}