package com.realifetech.sdk.campaignautomation

import com.realifetech.sdk.campaignautomation.data.model.ContentResponse
import com.realifetech.sdk.campaignautomation.domain.CampaignAutomationRepository
import javax.inject.Inject

class CampaignAutomationFeature @Inject constructor(private val campaignAutomationRepo: CampaignAutomationRepository) {

    fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: ContentResponse?) -> Unit
    ) {
        campaignAutomationRepo.getContentByExternalId(externalId, callback)
    }

}