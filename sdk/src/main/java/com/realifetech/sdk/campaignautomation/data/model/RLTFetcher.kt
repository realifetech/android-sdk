package com.realifetech.sdk.campaignautomation.data.model

import android.util.Log
import android.view.View
import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.analytics.Analytics
import com.realifetechCa.type.ContentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RLTFetcher @Inject constructor(private val analytics: Analytics) {

    private var factories: Map<ContentType, RLTCreatableFactory<*>> = emptyMap()

    fun setFactories(factories: Map<ContentType, RLTCreatableFactory<*>>) {
        this.factories = factories
    }

    fun fetch(
        location: String,
        callback: (error: Exception?, response: List<View?>) -> Unit
    ) {
        fetch(location, factories, callback)
    }


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
                                        val dictionary = eventDictionary(
                                            response.campaignId,
                                            location,
                                            bannerDataModel.id.toString(),
                                            it.contentType.toString(),
                                            bannerDataModel.language.toString()
                                        )
                                        bannerDataModel.listener = {
                                            trackUserInteractionCreatable(
                                                dictionary
                                            )
                                        }
                                        list.add(
                                            (factories[ContentType.BANNER] as? RLTBannerFactory)?.create(
                                                bannerDataModel
                                            ) as View
                                        )
                                        trackLoadCreatable(
                                            dictionary
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

    private fun eventDictionary(
        campaignId: String,
        location: String,
        contentId: String,
        contentType: String,
        languageCode: String,
    ): MutableMap<String, String> {
        val map = mutableMapOf<String, String>()
        map[CAMPAIGN_ID] = campaignId
        map[EXTERNAL_ID] = location
        map[CONTENT_ID] = contentId
        map[CONTENT_TYPE] = contentType
        map[LANGUAGE_CODE] = languageCode
        return map
    }

    private fun trackUserInteractionCreatable(
        map: Map<String, String>
    ) {
        analytics.track(
            USER,
            INTERACT_WITH_CONTENT,
            map,
            null,
        ) { error, result ->
            result.let {

            }
            error?.let {
                Log.e(this.javaClass.name, "Error while sending UserInteraction CA analytics")
            }
        }
    }

    private fun trackLoadCreatable(
        map: Map<String, String>
    ) {
        analytics.track(
            USER,
            LOAD_CONTENT,
            map,
            null,
        ) { error, result ->
            result.let {

            }
            error?.let {
                Log.e(this.javaClass.name, "Error while sending Loading CA analytics")
            }
        }
    }

    companion object {
        private const val CAMPAIGN_ID = "campaignId"
        private const val EXTERNAL_ID = "externalId"
        private const val CONTENT_ID = "contentId"
        private const val CONTENT_TYPE = "contentType"
        private const val LANGUAGE_CODE = "languageCode"
        private const val USER = "user"
        private const val LOAD_CONTENT = "loadContent"
        private const val INTERACT_WITH_CONTENT = "interactWithContent"
    }

}


