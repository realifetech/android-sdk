package com.realifetech.sdk.di.features.modules

import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.identity.sso.data.SSODataSource
import com.realifetech.sdk.identity.sso.data.SSODatasourceImpl
import dagger.Module
import dagger.Provides

@Module
object IdentityModule {

    @Provides
    fun webPageDataSource(
        apolloClient: ApolloClient
    ): SSODataSource {
        return SSODatasourceImpl(apolloClient)
    }
}