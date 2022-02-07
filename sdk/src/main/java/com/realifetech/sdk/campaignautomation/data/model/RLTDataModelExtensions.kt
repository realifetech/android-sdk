package com.realifetech.sdk.campaignautomation.data.model

import com.google.gson.Gson
import com.realifetechCa.GetContentByExternalIdQuery
import com.realifetechCa.type.ContentType

inline fun <reified T> convert(item: GetContentByExternalIdQuery.Item): T {
    val data = Gson().toJson(item.data)
    when (item.contentType) {
        ContentType.BANNER -> {
            return Gson().fromJson(data, BannerDataModel::class.java) as T
        }
        else -> {
            throw Exception()
        }
    }

}


