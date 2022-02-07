package com.realifetech.sdk.campaignautomation.data.model

interface RLTBannerFactory : RLTCreatableFactory{
   override fun create(bannerDataModel: BannerDataModel): RLTCreatable?
}
