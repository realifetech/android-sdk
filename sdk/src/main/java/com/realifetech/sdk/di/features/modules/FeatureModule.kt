package com.realifetech.sdk.di.features.modules

import android.content.Context
import com.realifetech.sdk.analytics.Analytics
import com.realifetech.sdk.analytics.domain.AnalyticsEngine
import com.realifetech.sdk.analytics.domain.AnalyticsStorage
import com.realifetech.sdk.audiences.Audiences
import com.realifetech.sdk.audiences.repository.AudiencesRepository
import com.realifetech.sdk.campaignautomation.CampaignAutomation
import com.realifetech.sdk.campaignautomation.data.model.RLTFetcher
import com.realifetech.sdk.campaignautomation.domain.CampaignAutomationRepository
import com.realifetech.sdk.communicate.Communicate
import com.realifetech.sdk.communicate.domain.PushNotificationsTokenStorage
import com.realifetech.sdk.core.data.database.preferences.auth.AuthTokenStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.ColorPallet
import com.realifetech.sdk.core.utils.DeviceCalendar
import com.realifetech.sdk.core.utils.NetworkUtil
import com.realifetech.sdk.di.features.FeatureScope
import com.realifetech.sdk.general.General
import com.realifetech.sdk.general.domain.DeviceRepository
import com.realifetech.sdk.general.domain.SdkInitializationPrecondition
import com.realifetech.sdk.identity.Identity
import com.realifetech.sdk.identity.domain.IdentityRepository
import com.realifetech.sdk.sell.Sell
import com.realifetech.sdk.sell.basket.BasketFeature
import com.realifetech.sdk.sell.fulfilmentpoint.FulfilmentPointFeature
import com.realifetech.sdk.sell.order.OrderFeature
import com.realifetech.sdk.sell.payment.PaymentFeature
import com.realifetech.sdk.sell.product.ProductFeature
import com.realifetech.sdk.sell.weboredering.WebOrderingFeature
import com.realifetech.sdk.sell.weboredering.WebViewWrapper
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
object FeatureModule {

    @FeatureScope
    @Provides
    fun webViewProvider(
        configuration: ConfigurationStorage,
        networkUtil: NetworkUtil,
        storage: AuthTokenStorage
    ) =
        WebViewWrapper(networkUtil, configuration, storage)

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
    ): Communicate {
        val communicate = Communicate(tokenStorage, realifetechApiV3Service, context)
        communicate.resendPendingToken()
        return communicate
    }

    @FeatureScope
    @Provides
    internal fun audiences(
        audiencesRepository: AudiencesRepository,
        context: Context
    ) = Audiences(audiencesRepository, Dispatchers.IO, Dispatchers.Main, context)

    @FeatureScope
    @Provides
    internal fun general(
        deviceRepository: DeviceRepository,
        sdkInitializationPrecondition: SdkInitializationPrecondition,
        configuration: ConfigurationStorage,
        colorPallet: ColorPallet
    ): General =
        General(
            deviceRepository,
            sdkInitializationPrecondition,
            configuration,
            colorPallet
        )

    @FeatureScope
    @Provides
    fun analytics(
        analyticsEngine: AnalyticsEngine,
        analyticsStorage: AnalyticsStorage,
        general: General,
        deviceCalendar: DeviceCalendar,
        configuration: ConfigurationStorage
    ): Analytics =
        Analytics(
            analyticsEngine,
            analyticsStorage,
            general,
            Dispatchers.IO,
            Dispatchers.Main,
            deviceCalendar,
            configuration
        )


    @FeatureScope
    @Provides
    fun identity(
        identityRepository: IdentityRepository,
        webViewWrapper: WebViewWrapper,
        storage: AuthTokenStorage,
        configuration: ConfigurationStorage,
        analytics: Analytics
    ): Identity =
        Identity(
            webViewWrapper,
            identityRepository,
            Dispatchers.IO,
            Dispatchers.Main,
            storage,
            configuration,
            analytics
        )

    @FeatureScope
    @Provides
    fun campaignAutomation(
        campaignAutomationRepository: CampaignAutomationRepository,
        rltFetcher: RLTFetcher
    ): CampaignAutomation =
        CampaignAutomation(
            campaignAutomationRepository,
            rltFetcher
        )

}