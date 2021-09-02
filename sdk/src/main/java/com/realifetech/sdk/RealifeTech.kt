package com.realifetech.sdk

import android.content.Context
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.audiences.Audiences
import com.realifetech.sdk.communicate.Communicate
import com.realifetech.sdk.content.Content
import com.realifetech.sdk.core.data.config.CoreConfiguration
import com.realifetech.sdk.core.domain.RLTConfiguration
import com.realifetech.sdk.di.core.CoreModule
import com.realifetech.sdk.di.core.DaggerCoreComponent
import com.realifetech.sdk.di.features.modules.*
import com.realifetech.sdk.general.General
import com.realifetech.sdk.sell.Sell

object RealifeTech : BaseRealifetech() {


    fun configureSdk(context: Context, configuration: CoreConfiguration) {
        RLTConfiguration.set(configuration)
        val coreComponent = DaggerCoreComponent.builder()
            .coreModule(CoreModule(context))
            .build()
        coreComponent.newFeaturesComponent(
            FeatureModule,
            GeneralModule,
            CommunicateModule,
            SellModule,
            AnalyticsModule(),
            ContentModule
        ).inject(this)
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

    fun set(webOrderingJourneyUrl: String) {
        RLTConfiguration.ORDERING_JOURNEY_URL = webOrderingJourneyUrl
    }

    fun clearAllCachedData() {
        apolloClient.clearNormalizedCache()
    }

}