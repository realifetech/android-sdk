package com.realifetech.sdk.campaignautomation

import com.realifetech.sdk.campaignautomation.data.model.ContentResponse
import com.realifetech.sdk.campaignautomation.domain.CampaignAutomationRepository
import com.realifetechCa.GetContentByExternalIdQuery
import javax.inject.Inject

class CampaignAutomationFeature @Inject constructor(private val campaignAutomationRepo: CampaignAutomationRepository) {

    fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit
    ) {
        campaignAutomationRepo.getContentByExternalId(externalId, callback)
    }

}