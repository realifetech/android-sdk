package com.realifetech.sdk.di.features.modules

import com.apollographql.apollo3.ApolloClient
import com.realifetech.sdk.content.screen.data.datasource.ScreenDataSourceImpl
import com.realifetech.sdk.content.screen.data.datasource.ScreensDataSource
import com.realifetech.sdk.content.webPage.data.WebPageDataSource
import com.realifetech.sdk.content.webPage.data.WebPageDataSourceImpl
import com.realifetech.sdk.content.widgets.data.datasource.WidgetsDataSource
import com.realifetech.sdk.content.widgets.data.datasource.WidgetsDataSourceImpl
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
        return WidgetsDataSourceImpl(apolloClient)
    }

    @Provides
    fun screensDataSource(
        apolloClient: ApolloClient
    ): ScreensDataSource {
        return ScreenDataSourceImpl(apolloClient)
    }


}