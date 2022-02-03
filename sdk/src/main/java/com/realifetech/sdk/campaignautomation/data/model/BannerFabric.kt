package com.realifetech.sdk.campaignautomation.data.model

import com.google.gson.Gson
import com.realifetechCa.GetContentByExternalIdQuery

object BannerFabric {

//    fun newCreate(item: GetContentByExternalIdQuery.Item): GetContentByExternalIdQuery.Item {
//        val data = Gson().toJson(null)
//        return Gson().fromJson(data, BannerDataModel::class.java)
//    }

    fun create(json: Any?): BannerDataModel {
        val data = Gson().toJson(json)
        return Gson().fromJson(data, BannerDataModel::class.java)
    }
}
