package com.realifetech.sdk.di.features.modules

import com.apollographql.apollo3.ApolloClient
import com.realifetech.sdk.access.data.AccessDataSource
import com.realifetech.sdk.access.data.AccessDataSourceImpl
import com.realifetech.sdk.access.domain.AccessRepository
import dagger.Module
import dagger.Provides

@Module
class AccessModule {
    @Provides
    fun accessRepository(accessDataSource: AccessDataSource) = AccessRepository(accessDataSource)

    @Provides
    fun accessDataSource(apolloClient: ApolloClient): AccessDataSource {
        return AccessDataSourceImpl(apolloClient)
    }
}