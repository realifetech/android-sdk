package com.realifetech.sdk.campaignautomation.data.model

import android.util.Log
import com.realifetech.sdk.RealifeTech
import com.realifetechCa.type.ContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RLTFetcherV2 @Inject constructor() {


    private val bannerFactory: RLTBannerFactory? = null


//    val factories = mutableMapOf<>()

    fun fetch(
        location: String,
        callback: (error: Exception?, response: List<RLTDataModel>?) -> Unit
    ) {
        val list = mutableListOf<RLTDataModel>()
        RealifeTech.getCampaignAutomation()
            .getContentByExternalId(location) { error, response ->
                GlobalScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        error?.let {
                            callback(error, null)
                        }
                        response?.let {
                            response.items?.forEach {
                                when (it?.contentType) {
                                    ContentType.BANNER -> {
                                        Log.d("RLTFetcher", "WOW IT'S A MATCH ${it.data}")
                                        list.add(RLTBannerFactory().create(it))
                                    }
                                    else -> {

                                    }
                                }
                                callback(null, list)
                            }
                            Log.d("RLTFetcherV2", list.size.toString())

                        }
                    }
                }
            }

    }

    private fun displayToast(message: String?) {
        Log.d("RLTFetcherV2", message!!)
    }

    companion object {

    }


}


