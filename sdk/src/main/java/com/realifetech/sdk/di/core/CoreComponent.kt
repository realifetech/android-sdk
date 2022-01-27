package com.realifetech.sdk.di.core

import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.di.features.FeatureComponent
import com.realifetech.sdk.di.features.modules.*
import dagger.Component

@CoreScope
@Component(modules = [CoreModule::class, RestClientV3Module::class, GraphQlModule::class])
interface CoreComponent {

    fun configurationStorage(): ConfigurationStorage

    fun newFeaturesComponent(
        featureModule: FeatureModule,
        generalModule: GeneralModule,
        communicateModule: CommunicateModule,
        sellModule: SellModule,
        analyticsModule: AnalyticsModule,
        contentModule: ContentModule,
        identityModule: IdentityModule
    ): FeatureComponent


}


