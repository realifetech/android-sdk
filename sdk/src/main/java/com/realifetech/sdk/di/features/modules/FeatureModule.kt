package com.realifetech.sdk.di.features.modules

import android.content.Context
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.analytics.data.AnalyticsEngine
import com.realifetech.sdk.analytics.data.AnalyticsStorage
import com.realifetech.sdk.communicate.Communicate
import com.realifetech.sdk.communicate.domain.PushNotificationsTokenStorage
import com.realifetech.sdk.core.database.preferences.Preferences
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.ColorPallet
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

    @Provides
    fun analytics(
        analyticsEngine: AnalyticsEngine,
        analyticsStorage: AnalyticsStorage
    ): Analytics =
        Analytics(analyticsEngine, analyticsStorage)

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

    @Provides
    internal fun communicate(
        tokenStorage: PushNotificationsTokenStorage,
        realifetechApiV3Service: RealifetechApiV3Service,
        context: Context
    ): Communicate =
        Communicate(tokenStorage, realifetechApiV3Service, context)


    internal fun general(
        deviceRepository: DeviceRepository,
        sdkInitializationPrecondition: SdkInitializationPrecondition,
        preferences: Preferences,
        colorPallet: ColorPallet
    ): General =
        General(deviceRepository, sdkInitializationPrecondition, preferences, colorPallet)
}