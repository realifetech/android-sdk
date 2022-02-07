package com.realifetech.sdk.campaignautomation.data.model

import com.realifetechCa.GetContentByExternalIdQuery

class RLTBannerFactory : RLTCreatableFactory<BannerDataModel> {


    override fun create(item: GetContentByExternalIdQuery.Item): BannerDataModel {
        return convert<BannerDataModel>(item)
    }

}
