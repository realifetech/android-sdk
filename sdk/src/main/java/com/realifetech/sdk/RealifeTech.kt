package com.realifetech.sdk

import android.content.Context
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.audiences.Audiences
import com.realifetech.sdk.campaignautomation.CampaignAutomationFeature
import com.realifetech.sdk.communicate.Communicate
import com.realifetech.sdk.content.Content
import com.realifetech.sdk.core.data.model.config.CoreConfiguration
import com.realifetech.sdk.di.Injector
import com.realifetech.sdk.general.General
import com.realifetech.sdk.identity.Identity
import com.realifetech.sdk.sell.Sell

object RealifeTech : BaseRealifetech() {


    fun configureSdk(context: Context, configuration: CoreConfiguration) {
        Injector.initialize(context, configuration).inject(this)
    }

    fun getGeneral(): General {
        return generalInstance
    }

    fun getCommunicate(): Communicate {
        return communicateInstance
    }

    fun getAnalytics(): Analytics {
        return analyticsInstance
    }

    fun getAudience(): Audiences {
        return audiencesInstance
    }

    fun getContent(): Content {
        return contentInstance
    }

    fun getSell(): Sell {
        return sellInstance
    }

    fun getIdentity(): Identity {
        return identityInstance
    }

    fun getCampaignAutomation(): CampaignAutomationFeature {
        return campaignAutomationInstance
    }

    fun set(webOrderingJourneyUrl: String) {
        configuration.webOrderingJourneyUrl = webOrderingJourneyUrl
    }

    fun clearAllCachedData() {
        apolloClient.clearNormalizedCache()
    }

}