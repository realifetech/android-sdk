package com.realifetech.sdk.di.features.modules

import android.content.Context
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.analytics.domain.AnalyticsEngine
import com.realifetech.sdk.analytics.domain.AnalyticsStorage
import com.realifetech.sdk.communicate.Communicate
import com.realifetech.sdk.communicate.domain.PushNotificationsTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.ColorPallet
import com.realifetech.sdk.di.features.FeatureScope
import com.realifetech.sdk.general.General
import com.realifetech.sdk.general.domain.DeviceRepository
import com.realifetech.sdk.general.domain.SdkInitializationPrecondition
import com.realifetech.sdk.sell.Sell
import com.realifetech.sdk.sell.basket.BasketFeature
import com.realifetech.sdk.sell.fulfilmentpoint.FulfilmentPointFeature
import com.realifetech.sdk.sell.order.OrderFeature
import com.realifetech.sdk.sell.payment.PaymentFeature
import com.realifetech.sdk.sell.product.ProductFeature
import com.realifetech.sdk.sell.weboredering.WebOrderingFeature
import dagger.Module
import dagger.Provides

@Module
object FeatureModule {

    @FeatureScope
    @Provides
    fun analytics(
        analyticsEngine: AnalyticsEngine,
        analyticsStorage: AnalyticsStorage
    ): Analytics =
        Analytics(analyticsEngine, analyticsStorage)

    @FeatureScope
    @Provides
    fun sell(
        paymentFeature: PaymentFeature,
        productFeature: ProductFeature,
        basketFeature: BasketFeature,
        orderFeature: OrderFeature,
        fulfillmentPointFeature: FulfilmentPointFeature,
        webOrderingFeature: WebOrderingFeature

    ) = Sell(
        paymentFeature,
        productFeature,
        basketFeature,
        orderFeature,
        fulfillmentPointFeature,
        webOrderingFeature
    )

    @FeatureScope
    @Provides
    internal fun communicate(
        tokenStorage: PushNotificationsTokenStorage,
        realifetechApiV3Service: RealifetechApiV3Service,
        context: Context
    ): Communicate =
        Communicate(tokenStorage, realifetechApiV3Service, context)

    @FeatureScope
    @Provides
    internal fun general(
        deviceRepository: DeviceRepository,
        sdkInitializationPrecondition: SdkInitializationPrecondition,
        configuration: ConfigurationStorage,
        colorPallet: ColorPallet
    ): General =
        General(deviceRepository, sdkInitializationPrecondition, configuration, colorPallet)
}