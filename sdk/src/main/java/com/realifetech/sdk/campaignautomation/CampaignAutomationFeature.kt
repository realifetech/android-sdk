package com.realifetech.sdk.campaignautomation

import com.realifetech.sdk.campaignautomation.data.model.RLTDataModel
import com.realifetech.sdk.campaignautomation.data.model.RLTFetcherV2
import com.realifetech.sdk.campaignautomation.domain.CampaignAutomationRepository
import com.realifetechCa.GetContentByExternalIdQuery
import javax.inject.Inject

class CampaignAutomationFeature @Inject constructor(
    private val campaignAutomationRepo: CampaignAutomationRepository,
    private val rltFetcherV2: RLTFetcherV2
) {

    internal fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit
    ) {
        campaignAutomationRepo.getContentByExternalId(externalId, callback)
    }

    fun fetch(
        location: String,
        callback: (error: Exception?, response: List<RLTDataModel>?) -> Unit
    ){
        rltFetcherV2.fetch(location, callback)
    }


}