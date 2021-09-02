package com.realifetech.sdk.di.features.analytics

import com.realifetech.sdk.di.features.modules.AnalyticsModule
import dagger.Subcomponent

@AnalyticsScope
@Subcomponent(modules = [AnalyticsModule::class])
interface AnalyticsComponent {
}