package com.realifetech.sdk.campaignautomation.data.model


interface RLTCreatableFactory {
    abstract fun <T : RLTDataModel> create(dataModel: T ): RLTCreatable?
    fun create(bannerDataModel: BannerDataModel): RLTCreatable?
}