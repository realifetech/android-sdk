package com.realifetech.sdk

import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.audiences.Audiences
import com.realifetech.sdk.communicate.Communicate
import com.realifetech.sdk.content.Content
import com.realifetech.sdk.core.domain.CoreConfiguration
import com.realifetech.sdk.core.network.graphQl.GraphQlModule
import com.realifetech.sdk.general.General
import com.realifetech.sdk.sell.Sell

object RealifeTech {
    fun getGeneral(): General {
        return General.instance
    }

    fun getCommunicate(): Communicate {
        return Communicate.instance
    }

    fun getAnalytics(): Analytics {
        return Analytics.instance
    }

    fun getAudience(): Audiences {
        return Audiences.instance
    }

    fun getContent(): Content {
        return Content.instance
    }

    fun getSell(): Sell {
        return Sell.INSTANCE
    }

    fun set(webOrderingJourneyUrl: String) {
        CoreConfiguration.webOrderingJourneyUrl = webOrderingJourneyUrl
    }

    fun clearAllCachedData() {
        GraphQlModule.apolloClient.clearNormalizedCache()
    }

}