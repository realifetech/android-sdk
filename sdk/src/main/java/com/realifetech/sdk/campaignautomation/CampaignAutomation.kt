package com.realifetech.sdk.campaignautomation

import android.view.View
import com.realifetech.GetContentByExternalIdQuery
import com.realifetech.sdk.campaignautomation.data.model.RLTContentItem
import com.realifetech.sdk.campaignautomation.data.model.RLTCreatableFactory
import com.realifetech.sdk.campaignautomation.data.model.RLTFetcher
import com.realifetech.sdk.campaignautomation.domain.CampaignAutomationRepository
import com.realifetech.type.ContentType
import javax.inject.Inject

class CampaignAutomation @Inject constructor(
    private val campaignAutomationRepo: CampaignAutomationRepository,
    private val rltFetcher: RLTFetcher
) {

    internal fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit
    ) {
        campaignAutomationRepo.getContentByExternalId(externalId, callback)
    }

    fun factories(factories: Map<ContentType, RLTCreatableFactory<*>>) {
        rltFetcher.setFactories(factories)
    }

    fun fetch(
        location: String,
        callback: (error: Exception?, response: List<View?>) -> Unit
    ) {
        rltFetcher.fetch(
            location,
            callback
        )
    }

    fun fetchRLTDataModels(
        location: String,
        callback: (error: Exception?, response: List<RLTContentItem?>) -> Unit
    ) {
        rltFetcher.fetchRLTDataModels(
            location,
            callback
        )
    }

    fun fetch(
        location: String,
        factories: Map<ContentType, RLTCreatableFactory<*>>,
        callback: (error: Exception?, response: List<View?>) -> Unit
    ) {
        rltFetcher.fetch(
            location,
            factories,
            callback
        )
    }


}