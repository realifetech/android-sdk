package com.realifetech.sdk.di.features

import com.realifetech.sdk.BaseRealifetech
import com.realifetech.sdk.di.features.modules.*
import com.realifetech.sdk.sell.weboredering.WebOrderingFragment
import dagger.Subcomponent

@FeatureScope
@Subcomponent(
    modules = [FeatureModule::class, ContentModule::class, AnalyticsModule::class, AudiencesModule::class,
        GeneralModule::class, SellModule::class, CommunicateModule::class]
)
interface FeatureComponent {

    fun inject(realifetech: BaseRealifetech)
    fun inject(webOrderingFragment: WebOrderingFragment)

}