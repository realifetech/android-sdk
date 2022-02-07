package com.realifetech.sdk.campaignautomation

import com.realifetech.sdk.campaignautomation.data.model.RLTCreatable
import com.realifetech.sdk.campaignautomation.data.model.RLTCreatableFactory
import com.realifetech.sdk.campaignautomation.data.model.RLTFetcher
import com.realifetech.sdk.campaignautomation.domain.CampaignAutomationRepository
import com.realifetechCa.GetContentByExternalIdQuery
import com.realifetechCa.type.ContentType
import javax.inject.Inject

class CampaignAutomationFeature @Inject constructor(
    private val campaignAutomationRepo: CampaignAutomationRepository,
    private val rltFetcher: RLTFetcher
) {

    internal fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit
    ) {
        campaignAutomationRepo.getContentByExternalId(externalId, callback)
    }

    fun fetch(
        location: String,
        factories: Map<ContentType, RLTCreatableFactory<*>>,
        callback: (error: Exception?, response: List<RLTCreatable?>) -> Unit
    ) {
        rltFetcher.fetch(
            location,
            factories,
            callback
        )
    }


}