package com.realifetech.sdk

import com.apollographql.apollo.ApolloClient
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.audiences.Audiences
import com.realifetech.sdk.communicate.Communicate
import com.realifetech.sdk.content.Content
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.general.General
import com.realifetech.sdk.sell.Sell
import javax.inject.Inject

abstract class BaseRealifetech {

    @Inject
    lateinit var generalInstance: General

    @Inject
    lateinit var contentInstance: Content

    @Inject
    lateinit var communicateInstance: Communicate

    @Inject
    lateinit var audiencesInstance: Audiences

    @Inject
    lateinit var analyticsInstance: Analytics

    @Inject
    lateinit var sellInstance: Sell

    @Inject
    lateinit var apolloClient: ApolloClient

    @Inject
    lateinit var configuration: ConfigurationStorage
}