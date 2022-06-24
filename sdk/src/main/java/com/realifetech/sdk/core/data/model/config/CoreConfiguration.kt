package com.realifetech.sdk.core.data.model.config

import com.realifetech.sdk.core.data.constants.ConfigConstants

data class CoreConfiguration(
    val appCode: String=ConfigConstants.appCode,
    val clientSecret: String= ConfigConstants.clientSecret,
    var apiUrl: String = ConfigConstants.apiUrl,
    var graphApiUrl: String = ConfigConstants.graphApiUrl,
    var webOrderingJourneyUrl: String = ConfigConstants.webOrderingJourneyUrl,
)
