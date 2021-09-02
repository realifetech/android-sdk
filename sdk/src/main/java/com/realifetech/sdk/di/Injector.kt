package com.realifetech.sdk.di

import android.content.Context
import com.realifetech.sdk.di.core.CoreModule
import com.realifetech.sdk.di.core.DaggerCoreComponent
import com.realifetech.sdk.di.features.FeatureComponent
import com.realifetech.sdk.di.features.modules.*

class Injector {
    companion object {
        private lateinit var component: FeatureComponent
        fun initialize(context: Context): FeatureComponent {
            val coreComponent = DaggerCoreComponent.builder()
                .coreModule(CoreModule(context))
                .build()
            component = coreComponent.newFeaturesComponent(
                FeatureModule,
                GeneralModule,
                CommunicateModule,
                SellModule,
                AnalyticsModule(),
                ContentModule
            )
            return component
        }

        fun getComponent(): FeatureComponent {
            return component
        }
    }


}