package com.realifetech.sdk.campaignautomation.data.model

interface RLTBannerFactory : RLTCreatableFactory<BannerDataModel>{
   override fun create(dataModel: BannerDataModel): RLTViewCreatable?
}
