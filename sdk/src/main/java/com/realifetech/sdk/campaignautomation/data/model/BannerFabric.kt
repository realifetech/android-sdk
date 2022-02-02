package com.realifetech.sdk.campaignautomation.data.model

import com.google.gson.Gson

object BannerFabric {
    fun create(json: Any?): Banner {
        val data = Gson().toJson(json)
        return Gson().fromJson(data, Banner::class.java)
    }
}
