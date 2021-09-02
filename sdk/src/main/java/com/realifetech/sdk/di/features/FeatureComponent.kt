package com.realifetech.sdk.di.features

import com.realifetech.sdk.BaseRealifetech
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.audiences.Audiences
import com.realifetech.sdk.communicate.Communicate
import com.realifetech.sdk.content.Content
import com.realifetech.sdk.di.features.modules.*
import com.realifetech.sdk.general.General
import com.realifetech.sdk.sell.Sell
import dagger.Subcomponent

@FeatureScope
@Subcomponent(
    modules = [FeatureModule::class, ContentModule::class,
        GeneralModule::class, SellModule::class, CommunicateModule::class]
)
interface FeatureComponent {

    fun general(): General
    fun analytics(): Analytics
    fun communicate(): Communicate
    fun content(): Content
    fun sell(): Sell
    fun audiences(): Audiences

    fun inject(realifetech: BaseRealifetech)
}