package com.realifetech.sdk.di.core

import com.realifetech.sdk.di.features.FeatureComponent
import com.realifetech.sdk.di.features.modules.*
import dagger.Component

@CoreScope
@Component(modules = [CoreModule::class,RestClientV3Module::class, GraphQlModule::class])
interface CoreComponent {

    fun newFeaturesComponent(
        featureModule: FeatureModule,
        generalModule: GeneralModule,
        communicateModule: CommunicateModule,
        sellModule: SellModule,
        analyticsModule: AnalyticsModule,
        contentModule: ContentModule
    ): FeatureComponent


}


