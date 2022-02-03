package com.realifetech.sdk.campaignautomation.data.model

import android.util.Log
import com.realifetechCa.GetContentByExternalIdQuery
import com.realifetechCa.type.ContentType

class RLTFetcher(
    result: GetContentByExternalIdQuery.GetContentByExternalId?,
    filter: List<ContentType> = emptyList()
) {
    val list = mutableListOf<BannerDataModel>()
    init {

        result?.items?.forEach {
            when (it?.contentType) {
                ContentType.BANNER -> {
                    if (showBanner(filter)) {
                        Log.d("RLTFetcher", "WOW IT'S A MATCH ${it.data}")
                        list.add(BannerFabric.create(it))
                    } else {
                        Log.d("RLTFetcher", "Sorry but ${it.data} will be skipped ")
                    }


                }
                else -> {

                }
            }

        }

    }

    fun returnList(): List<BannerDataModel> {
        Log.d("RLTFetcher", list.size.toString())
        return list
    }

    private fun showBanner(filter: List<ContentType>?): Boolean {
        return if (filter?.isEmpty()!!) true
        else {
            filter.contains(ContentType.BANNER)
        }
    }


}