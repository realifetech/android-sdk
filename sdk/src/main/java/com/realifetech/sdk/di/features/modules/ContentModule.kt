package com.realifetech.sdk.di.features.modules

import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.content.screen.domain.ScreenBackendDataSource
import com.realifetech.sdk.content.screen.domain.ScreensDataSource
import com.realifetech.sdk.content.webPage.data.WebPageDataSource
import com.realifetech.sdk.content.webPage.data.WebPageDataSourceImpl
import com.realifetech.sdk.content.widgets.domain.WidgetsBackendDataSource
import com.realifetech.sdk.content.widgets.domain.WidgetsDataSource
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