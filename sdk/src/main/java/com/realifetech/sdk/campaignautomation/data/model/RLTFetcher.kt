package com.realifetech.sdk.campaignautomation.data.model

import android.view.View
import com.realifetech.sdk.RealifeTech
import com.realifetechCa.type.ContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RLTFetcher @Inject constructor() {

    fun fetch(
        location: String,
        factories: Map<ContentType, RLTCreatableFactory<*>>,
        callback: (error: Exception?, response: List<View?>) -> Unit
    ) {
        val list = mutableListOf<View?>()
        RealifeTech.getCampaignAutomation()
            .getContentByExternalId(location) { error, response ->
                GlobalScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        error?.let {
                            callback(error, emptyList())
                        }
                        response?.let {
                            response.items?.forEach {
                                when (it?.contentType) {
                                    ContentType.BANNER -> {
                                        val bannerDataModel = convert<BannerDataModel>(it)
                                        list.add(
                                            (factories[ContentType.BANNER] as? RLTBannerFactory)?.create(
                                                bannerDataModel
                                            )
                                                    as View
                                        )
                                    }
                                    else -> {

                                    }
                                }
                            }
                            callback(null, list)

                        }
                    }
                }
            }

    }


}


