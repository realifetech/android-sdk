package com.realifetech.sdk.di.features.modules

import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.campaignautomation.data.datasource.CampaignAutomationDataSource
import com.realifetech.sdk.campaignautomation.data.datasource.CampaignAutomationDataSourceImplementation
import dagger.Module
import dagger.Provides

@Module
object CampaignAutomationModule {
    @Provides
    internal fun campaignAutomationDataSource(apolloClient: ApolloClient): CampaignAutomationDataSource {
        return CampaignAutomationDataSourceImplementation(apolloClient)
    }
}