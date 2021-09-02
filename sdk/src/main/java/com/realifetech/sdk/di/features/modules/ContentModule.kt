package com.realifetech.sdk.di.features.modules

import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.content.Content
import com.realifetech.sdk.content.screen.domain.ScreenBackendDataSource
import com.realifetech.sdk.content.screen.domain.ScreenRepository
import com.realifetech.sdk.content.screen.domain.ScreensDataSource
import com.realifetech.sdk.content.webPage.domain.WebPageDataSource
import com.realifetech.sdk.content.webPage.domain.WebPageDataSourceImpl
import com.realifetech.sdk.content.webPage.domain.WebPageRepository
import com.realifetech.sdk.content.widgets.domain.WidgetsBackendDataSource
import com.realifetech.sdk.content.widgets.domain.WidgetsDataSource
import com.realifetech.sdk.content.widgets.domain.WidgetsRepository
import dagger.Module
import dagger.Provides

@Module
object ContentModule {

    @Provides
    fun webPageDataSource(
        apolloClient: ApolloClient
    ): WebPageDataSource {
        return WebPageDataSourceImpl(apolloClient)
    }

    @Provides
    fun widgetDataSource(
        apolloClient: ApolloClient
    ): WidgetsDataSource {
        return WidgetsBackendDataSource(apolloClient)
    }

    @Provides
    fun screensDataSource(
        apolloClient: ApolloClient
    ): ScreensDataSource {
        return ScreenBackendDataSource(apolloClient)
    }


}