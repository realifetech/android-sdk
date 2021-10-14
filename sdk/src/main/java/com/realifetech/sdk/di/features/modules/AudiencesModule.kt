package com.realifetech.sdk.di.features.modules

import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.audiences.data.AudiencesDataSource
import com.realifetech.sdk.audiences.data.AudiencesDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
object AudiencesModule {
    @Provides
    internal fun audiencesDataSource(apolloClient: ApolloClient): AudiencesDataSource {
        return AudiencesDataSourceImpl(apolloClient)
    }
}