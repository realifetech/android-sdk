package com.realifetech.sdk.sell.domain

import com.realifetech.core_sdk.domain.CoreConfiguration

class SellConfiguration {

    var webOrderingJourneyUrl = "https://ordering.realifetech.com/"
        set(value) {
            field = value
            CoreConfiguration.webOrderingJourneyUrl = value
        }

}