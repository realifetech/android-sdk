package com.realifetech.sdk.core.mocks

import com.realifetech.sdk.core.data.model.config.CoreConfiguration

object ConfigurationMocks {
    const val API_URL = "www.test.com/"
    const val GRAPHQL_URL = "www.test-graphql.com/"
    const val APP_CODE = "LS"
    const val CLIENT_SECRET = "it's a secret"
    const val WEB_ORDERING_URL = "www.ordering-test.com"
    const val DEVICE_ID = "0ea67cc4-b6e9-42fb-a9d7-2578b8d2ade7:com.realifetech.sample"
    val configuration =
        CoreConfiguration(APP_CODE, CLIENT_SECRET, API_URL, GRAPHQL_URL, WEB_ORDERING_URL)
    val defaultConfiguration = CoreConfiguration(APP_CODE, CLIENT_SECRET)
    val changeableConfiguration =  CoreConfiguration(APP_CODE, CLIENT_SECRET)

}