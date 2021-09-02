package com.realifetech.sdk.core.data.config

data class CoreConfiguration(
    val appCode: String,
    val clientSecret: String,
    var apiUrl: String = "https://api.livestyled.com/v3",
    var graphApiUrl: String = "https://graphql-eu.realifetech.com/",
    var webOrderingJourneyUrl: String = "https://ordering.realifetech.com/",
)
